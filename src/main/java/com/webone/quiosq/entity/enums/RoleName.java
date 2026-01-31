package com.webone.quiosq.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleName {
    ROLE_SYSTEM_ADMIN("ADMINISTRADOR SYSTEMA"),
    ROLE_ADMIN("ADMINISTRADOR"),
    ROLE_GARCOM("GARCOM"),
    ROLE_COZINHA("COZINHA"),
    ROLE_CLIENTE("CLIENTE");

    private String decricao;
}
