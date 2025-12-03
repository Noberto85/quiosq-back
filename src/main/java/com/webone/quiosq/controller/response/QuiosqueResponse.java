package com.webone.quiosq.controller.response;

import java.util.UUID;
import lombok.Data;

@Data
public class QuiosqueResponse {

    private UUID id;

    private String nome;

    private String cnpj;

}
