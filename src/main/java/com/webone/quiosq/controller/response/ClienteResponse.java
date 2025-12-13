package com.webone.quiosq.controller.response;

import java.util.UUID;
import lombok.Data;

@Data
public class ClienteResponse {

    private String telefone;
    private UUID quiosqueId;
}
