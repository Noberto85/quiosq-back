package com.webone.quiosq.handler;

import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.itg.MercadoApiService;
import com.webone.quiosq.itg.request.Identification;
import com.webone.quiosq.itg.request.Payer;
import com.webone.quiosq.itg.request.PixRequest;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PagamentoPix extends PagamentoHandle {

    private final MercadoApiService mercadoApiService;

    @Override
    protected boolean canHandle(String type) {
        return type.equals("pix");
    }

    @Override
    protected void handle(PedidoRequest request, String acessToken) {
        String idempotencyKey = UUID.randomUUID().toString();
        PixRequest pixRequest = new PixRequest();
        pixRequest.setDescription("Descricação teste");
        pixRequest.setTransactionAmount(request.getTotal());
        pixRequest.setNotificationUrl(
            "https://437ba9905884.ngrok-free.app/api/webhook/mercadopago");
        pixRequest.setPaymentMethodId("pix");
        pixRequest.setExternalReference(
            String.format("%s-%s", request.getPedidoId(), request.getCodePedido()));
        pixRequest.setPayer(Payer.builder()
            .email(request.getPagamento().getEmail())
            .firstName("RAFAEL NOBERTO")
            .identification(Identification.builder()
                .number(request.getClienteId())
                .type("CPF")
                .build())
            .build());

        mercadoApiService.createPix(acessToken, idempotencyKey, pixRequest);
    }
}
