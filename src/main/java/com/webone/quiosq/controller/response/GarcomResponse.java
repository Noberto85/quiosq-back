package com.webone.quiosq.controller.response;


import com.webone.quiosq.entity.Garcom;
import lombok.Data;

@Data
public class GarcomResponse {

    private Long id;
    private String nome;
    private String cpf;
    private Boolean status;

    public GarcomResponse(Garcom garcom) {
        this.id = garcom.getId();
        this.nome = garcom.getNome();
        this.cpf = garcom.getCpf();
        this.status = garcom.getAtivo();
    }
}
