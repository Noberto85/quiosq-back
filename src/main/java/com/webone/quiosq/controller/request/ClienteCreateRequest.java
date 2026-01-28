package com.webone.quiosq.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class ClienteCreateRequest {

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private String nome;

    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private String telefone;


    @NotNull(message = "O campo não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private String password;
}
