package com.webone.quiosq.dto;

import com.webone.quiosq.entity.Role;
import com.webone.quiosq.entity.enums.RoleName;
import lombok.Data;

@Data
public class RoleDto {
private RoleName id;
private String role;

    public RoleDto(Role role) {
        this.id = role.getNome();
        this.role = role.getNome().getDecricao();
    }
}
