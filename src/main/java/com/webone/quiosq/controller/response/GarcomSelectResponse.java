package com.webone.quiosq.controller.response;


import com.webone.quiosq.entity.Garcom;
import lombok.Data;

@Data
public class GarcomSelectResponse {

    private Long code;
    private String name;

    public GarcomSelectResponse(Garcom garcom) {
        this.code = garcom.getId();
        this.name = garcom.getNome();
    }
}
