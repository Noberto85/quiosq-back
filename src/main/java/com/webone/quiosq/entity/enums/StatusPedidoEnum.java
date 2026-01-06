package com.webone.quiosq.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusPedidoEnum {
    AGUARDANDO_PAGAMENTO("pending"),
    EM_PREPARACAO("preparing"),
    PRONTO("delivering"),
    ENTREGUE("completed"),
    CANCELADO("cancelled");

    private String descricao;
    
}
