package com.webone.quiosq.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedidoEnum {
    RECEBIDO("Pedido recebido"),
    EM_PREPARACAO("Em preparação"),
    PRONTO("Pronto para retirada/entrega"),
    ENTREGUE("Entregue ao cliente"),
    CANCELADO("Pedido cancelado"),
    PAGO("Pagamento confirmado");

    private String descricao;


}
