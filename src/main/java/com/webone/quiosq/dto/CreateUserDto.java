package com.webone.quiosq.dto;


import com.webone.quiosq.entity.enums.RoleName;
import java.util.UUID;

public record CreateUserDto(

        String email,
        String nome,
        String password,
        RoleName role,
        UUID quiosqueId
) {
}
