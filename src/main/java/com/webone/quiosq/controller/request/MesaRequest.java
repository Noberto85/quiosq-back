package com.webone.quiosq.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MesaRequest {
    private Long id;

    @NotNull(message = "O campo do garçom não pode ser nulo")
    private Long garcomId;

    @NotNull(message = "O campo do quiosque não pode ser nulo")
    @Min(value = 1, message = "O número da mesa deve ser maior que zero")
    private Integer numero;
}