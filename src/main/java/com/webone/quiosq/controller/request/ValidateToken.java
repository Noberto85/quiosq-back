package com.webone.quiosq.controller.request;

import jakarta.validation.constraints.NotNull;

public record ValidateToken(
    @NotNull String telefone,
    @NotNull String token) {

}
