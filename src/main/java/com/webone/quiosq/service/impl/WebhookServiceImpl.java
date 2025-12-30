package com.webone.quiosq.service.impl;

import com.webone.quiosq.dto.MercadoPagoWebhookDTO;
import com.webone.quiosq.entity.MercadoPagoToken;
import com.webone.quiosq.entity.Pagamento;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import com.webone.quiosq.itg.MercadoApiService;
import com.webone.quiosq.itg.response.StatusPagamentoApiResponse;
import com.webone.quiosq.repository.PagamentoRepository;
import com.webone.quiosq.service.MercadoPagoTokenService;
import com.webone.quiosq.service.WebhookService;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class WebhookServiceImpl implements WebhookService {

    private final PagamentoRepository repository;
    private final MercadoPagoTokenService mercadoPagoTokenService;
    private final MercadoApiService mercadoApiService;

    @Override
    public void validatePagamento(MercadoPagoWebhookDTO payload) {
        log.info("RECEBENDO WEBHOOK: {} ", payload);
        if (Objects.nonNull(payload.getData())) {
            if (payload.getAction().equals("payment.updated")) {
                final MercadoPagoToken mercadoPagoToken = mercadoPagoTokenService.findById(
                    payload.getUserId());
                final var status = mercadoApiService.verificaStatus(
                    mercadoPagoToken.getAccessToken(), payload.getData().getId());

                switch (status.getStatus()) {
                    case "approved":
                        updatePagamento(status, StatusPedidoEnum.EM_PREPARACAO,
                            LocalDateTime.now());
                        return;
                    case "rejected":
                        updatePagamento(status, StatusPedidoEnum.CANCELADO);
                        return;
                    case "cancelled":
                        updatePagamento(status, StatusPedidoEnum.CANCELADO);
                    default:
                        return;
                }

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
        repository.save(pagamento);
    }
}
