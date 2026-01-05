package com.webone.quiosq.handler;

import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.itg.MercadoApiService;
import com.webone.quiosq.itg.request.Identification;
import com.webone.quiosq.itg.request.Payer;
import com.webone.quiosq.itg.request.PixRequest;
import com.webone.quiosq.itg.response.PagamentoApiResponse;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PagamentoPix extends PagamentoHandle {

    private static final String PATH = "/api/webhook/mercadopago";
    private static final int MINUTES_EXP = 15;
    private static final String PIX = "pix";
    private final MercadoApiService mercadoApiService;
    private final String notificationBase;

    public PagamentoPix(MercadoApiService mercadoApiService,
        @Value("${notification.base}") String notificationBase) {
        this.mercadoApiService = mercadoApiService;
        this.notificationBase = notificationBase;
    }

    @Override
    protected boolean canHandle(String type) {
        return type.equals(PIX);
    }

    @Override
    protected PagamentoApiResponse handle(PedidoRequest request, String acessToken) {
        String idempotencyKey = UUID.randomUUID().toString();
        PixRequest pixRequest = new PixRequest();
        pixRequest.setDescription(String.format("Pedido: #%s", request.getCodePedido()));
        pixRequest.setTransactionAmount(request.getTotal());
        pixRequest.setDateOfExpiration(OffsetDateTime.now().plusMinutes(MINUTES_EXP));
        pixRequest.setNotificationUrl(String.format("%s%s", notificationBase, PATH));
        pixRequest.setPaymentMethodId(request.getPagamento().getMetodo());
        pixRequest.setExternalReference(
            String.format("%s-%s", request.getPedidoId(), request.getCodePedido()));
        pixRequest.setPayer(Payer.builder()
            .email(request.getEmail())
            .firstName(request.getNome())
            .identification(Identification.builder()
                .number(request.getPagamento().getDocumento())
                .type(request.getPagamento().getDocumento())
                .build())
            .build());
        return mercadoApiService.createPix(acessToken, idempotencyKey, pixRequest);

    }
}
