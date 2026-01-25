package com.webone.quiosq.dto;

import com.webone.quiosq.projection.TotalVendidoProjection;
import lombok.Data;

@Data
public class TotalVendidoDto {

    private String descricao;
    private Integer totalVendido;

    public TotalVendidoDto(TotalVendidoProjection projection) {
        this.descricao = projection.getdescricao();
        this.totalVendido = projection.gettotalVendido();
    }
}
