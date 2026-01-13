package com.webone.quiosq.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedidoEnum {
    AGUARDANDO_PAGAMENTO("pending"),
    AGUARDANDO_PREPARO("awaiting_preparation"),
    EM_PREPARACAO("preparing"),
    PRONTO("delivering"),
    ENTREGUE("completed"),
    CANCELADO("cancelled");

    private String descricao;
    
}
