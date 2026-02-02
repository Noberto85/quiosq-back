package com.webone.quiosq.controller.request;

import java.util.UUID;
import lombok.Data;

@Data
public class GarcomRequest {

    private UUID id;
    private String nome;
    private String cpf;

}

