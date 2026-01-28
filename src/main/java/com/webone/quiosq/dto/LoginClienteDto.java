package com.webone.quiosq.dto;

import java.util.UUID;

public record LoginClienteDto(

        String telefone,
        String password,
        UUID quiosqueId,
        Integer mesa

)  {
}
