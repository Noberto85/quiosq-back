package com.webone.quiosq.controller.response;

import java.util.UUID;
import lombok.Data;

@Data
public class IdentifcacaoResponse {

    private String garcom;
    private Integer mesa;
    private String quiosque;
    private UUID quiosqueId;

}
