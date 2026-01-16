package com.webone.quiosq.controller.request;

import jakarta.validation.constraints.NotNull;

public record CategoriaRequest(
    Long id,
   @NotNull(message = "Descrição não pode ser nulo!") String descricao
) {

}
