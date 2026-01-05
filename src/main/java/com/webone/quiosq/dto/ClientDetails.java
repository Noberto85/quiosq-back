package com.webone.quiosq.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ClientDetails {
    private Long clienteId;
    private String telefone;
    private UUID quiosqueId;
    private Integer mesa;
    private BigDecimal taxa;

}
