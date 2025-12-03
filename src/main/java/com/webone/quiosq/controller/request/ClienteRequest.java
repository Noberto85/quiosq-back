package com.webone.quiosq.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClienteRequest {

    @NotNull(message = "O campo do nome não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private String nome;
    @NotNull(message = "O campo do telefone não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private String telefone;
}
