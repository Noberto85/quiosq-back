package com.webone.quiosq.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EstastisticasDto {

    private Integer qtdTotQuiosque;
    private Integer qtdTotQuiosqueInativo;
    private Integer qtdClientesCad;
}
