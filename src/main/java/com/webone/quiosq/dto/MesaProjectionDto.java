package com.webone.quiosq.dto;

import com.webone.quiosq.projection.MesaInfoProjection;
import java.util.UUID;
import lombok.Data;

@Data
public class MesaProjectionDto {

    private UUID quiosqueId;
    private String quiosque;
    private String garcom;
    private Integer mesa;

    public MesaProjectionDto(MesaInfoProjection mesaInfoProjection) {
        this.quiosqueId = mesaInfoProjection.getQuiosqueId();
        this.quiosque = mesaInfoProjection.getQuiosqueNome();
        this.garcom = mesaInfoProjection.getGarcomNome();
        this.mesa = mesaInfoProjection.getMesaNumero();
    }
}
