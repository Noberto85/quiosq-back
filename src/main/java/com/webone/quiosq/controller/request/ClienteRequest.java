package com.webone.quiosq.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class ClienteRequest {

    @NotNull(message = "O campo do telefone não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private String telefone;

    @NotNull(message = "O campo do quiosque não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private UUID quiosqueId;

    @NotNull(message = "O campo do mesa não pode ser nulo")
    @NotEmpty(message = "Não pode ser vazio")
    private Integer mesa;
}
