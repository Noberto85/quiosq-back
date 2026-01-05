package com.webone.quiosq.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class JwtPayload {
    private String subject;
    private Integer numeroMesa;
    private Long mesaId;
    private UUID quiosqueId;
    private String role;

}
