package com.webone.quiosq.controller.response;

import com.webone.quiosq.entity.User;
import java.util.UUID;
import lombok.Data;

@Data
public class GarcomSelectResponse {

    private UUID id;
    private String nome;
    private String cpf;
    private Boolean status;

    public GarcomSelectResponse(User garcom) {
        this.id = garcom.getId();
        this.nome = garcom.getNome();
        this.cpf = garcom.getCpf();
        this.status = garcom.getActive();
    }
}
