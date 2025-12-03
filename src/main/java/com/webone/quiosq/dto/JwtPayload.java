package com.webone.quiosq.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class JwtPayload {
    private String subject;
    private Integer mesaId;// cliente
    private UUID quiosqueId;      // escopo do cliente
    private String role;

}
