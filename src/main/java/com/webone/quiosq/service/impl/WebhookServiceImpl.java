package com.webone.quiosq.service.impl;

import com.webone.quiosq.dto.MercadoPagoWebhookDTO;
import com.webone.quiosq.entity.MercadoPagoToken;
import com.webone.quiosq.entity.Pagamento;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import com.webone.quiosq.exception.CodeErro.NaoAutorizadoError;
import com.webone.quiosq.exception.NaoAutorizadoException;
import com.webone.quiosq.itg.MercadoApiService;
import com.webone.quiosq.itg.response.StatusPagamentoApiResponse;
import com.webone.quiosq.repository.PagamentoRepository;
import com.webone.quiosq.service.MercadoPagoTokenService;
import com.webone.quiosq.service.WebhookService;
import com.webone.quiosq.ws.PixStatusBroadcaster;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class WebhookServiceImpl implements WebhookService {

    private static final String APPROVED = "approved";
    private static final String REJECTED = "rejected";
    private static final String CANCELLED = "cancelled";
    private static final String PAYMENT_UPDATED = "payment.updated";
    private final PagamentoRepository repository;
    private final MercadoPagoTokenService mercadoPagoTokenService;
    private final MercadoApiService mercadoApiService;
    private final PixStatusBroadcaster broadcaster;
    private final String whSecret;

    public WebhookServiceImpl(PagamentoRepository repository,
        MercadoPagoTokenService mercadoPagoTokenService, MercadoApiService mercadoApiService,
        PixStatusBroadcaster broadcaster, @Value("${webhook.secret}") String whSecret) {
        this.repository = repository;
        this.mercadoPagoTokenService = mercadoPagoTokenService;
        this.mercadoApiService = mercadoApiService;
        this.broadcaster = broadcaster;
        this.whSecret = whSecret;
    }


    @Override
    public void validatePagamento(MercadoPagoWebhookDTO payload, Map<String, String> headers) {
        log.info("RECEBENDO WEBHOOK: {} ", payload);

        if (Objects.nonNull(payload.getData())) {
            if (secretValidation(headers, payload)) {

                if (payload.getAction().equals(PAYMENT_UPDATED)) {
                    final MercadoPagoToken mercadoPagoToken = mercadoPagoTokenService.findById(
                        payload.getUserId());
                    final var status = mercadoApiService.verificaStatus(
                        mercadoPagoToken.getAccessToken(),
                        Long.parseLong(payload.getData().getId()));

                    switch (status.getStatus()) {
                        case APPROVED:
                            updatePagamento(status, StatusPedidoEnum.EM_PREPARACAO,
                                LocalDateTime.now());
                            broadcaster.broadcast("{\"status\":\"approved\"}");
                            return;
                        case REJECTED:
                            updatePagamento(status, StatusPedidoEnum.CANCELADO);
                            return;
                        case CANCELLED:
                            updatePagamento(status, StatusPedidoEnum.CANCELADO);

                    }

                }
            } else {
                throw new NaoAutorizadoException(
                    NaoAutorizadoError.SUSPEIRA_FRAUDE_ERROR.getCodeErro());
            }
        }
    }

    private void updatePagamento(StatusPagamentoApiResponse payload, StatusPedidoEnum status) {
        updatePagamento(payload, status, null);
    }

    private void updatePagamento(StatusPagamentoApiResponse payload, StatusPedidoEnum status,
        LocalDateTime dataApro) {
        Optional<Pagamento> byMpPagId = repository.findByMpPagId(
            payload.getId());
        Pagamento pagamento = byMpPagId.get();
        pagamento.setDataAprovacao(dataApro);
        pagamento.setStatus(payload.getStatus());
        pagamento.setStatusDetail(payload.getStatusDetail());
        pagamento.getPedido().setStatus(status);
        pagamento.setTransactionId(payload.getTransactionDetails().getTransactionId());
        repository.save(pagamento);
    }

    private boolean secretValidation(Map<String, String> headers,
        MercadoPagoWebhookDTO payload) {
        String signatureHeader = headers.get("x-signature");
        String[] parts = signatureHeader.split(",");
        String ts = parts[0].split("=")[1];
        String v1 = parts[1].split("=")[1];
        String template = String.format(
            "id:%s;request-id:%s;ts:%s;",
            payload.getData().getId(),
            headers.get("x-request-id"),
            ts
        );
        String generatedSignature = new HmacUtils("HmacSHA256", whSecret)
            .hmacHex(template);

        if (generatedSignature.equals(v1)) {
            log.info("Webhook válido!");
            return Boolean.TRUE;
        } else {
            log.warn("Assinatura inválida, possível fraude.");
            return Boolean.FALSE;
        }
    }
}
