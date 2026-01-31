package com.webone.quiosq.dto;

import jakarta.validation.constraints.NotNull;

public record LoginFuncionarioDto(

    @NotNull String login,
    @NotNull String password

) {

}
