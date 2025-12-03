package com.webone.quiosq.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusMesaEnum {
    OCUPADO("Ocupada"),
    LIVRE("Livre");

    private String descricao;


}
