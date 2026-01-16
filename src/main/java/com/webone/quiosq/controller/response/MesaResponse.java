package com.webone.quiosq.controller.response;


import com.webone.quiosq.entity.Garcom;
import com.webone.quiosq.entity.Mesa;
import java.util.Optional;
import lombok.Data;

@Data
public class MesaResponse {

    private Long id;

    private Integer numero;

    private String garcom;


    public MesaResponse(Mesa mesa) {
        this.id = mesa.getId();
        this.numero = mesa.getNumero();
        this.garcom = Optional.ofNullable(mesa.getGarcom())
            .map(Garcom::getNome)
            .orElse(null);

    }
}
