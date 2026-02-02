package com.webone.quiosq.controller.request;

import com.webone.quiosq.entity.enums.RoleName;
import jakarta.validation.constraints.NotNull;

public record FuncionarioCreateRequest(
    @NotNull String nome,
    @NotNull String cpf,
    @NotNull String telefone,
    @NotNull RoleName role
) {

}
