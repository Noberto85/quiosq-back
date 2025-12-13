package com.webone.quiosq.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MetodoPagamentoEnum {

    DEBTO("Cartao de Débito"), CREDITO("Cartao de Credito"), PIX("Pix");
    private String descricao;
}
