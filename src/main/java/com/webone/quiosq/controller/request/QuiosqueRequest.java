package com.webone.quiosq.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuiosqueRequest {

    @NotNull(message = "O campo do nome não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private String nome;

    @NotNull(message = "O campo do cnpj não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private String cnpj;

}
