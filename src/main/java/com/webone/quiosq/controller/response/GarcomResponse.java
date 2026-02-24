package com.webone.quiosq.controller.response;


import com.webone.quiosq.entity.User;
import java.util.UUID;
import lombok.Data;

@Data
public class GarcomResponse {

    private UUID id;
    private String nome;
    private String cpf;
    private Boolean status;
    private String role;

    public GarcomResponse(User garcom) {
        this.id = garcom.getId();
        this.nome = garcom.getNome();
        this.cpf = garcom.getCpf();
        this.status = garcom.getActive();
        this.role =
            !garcom.getRoles().isEmpty() ? garcom.getRoles().get(0).getNome().getDecricao() : null;
    }
}
