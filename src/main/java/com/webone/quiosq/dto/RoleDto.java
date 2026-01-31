package com.webone.quiosq.dto;

import com.webone.quiosq.entity.Role;
import lombok.Data;

@Data
public class RoleDto {
private Long id;
private String role;

    public RoleDto(Role role) {
        this.id = role.getId();
        this.role = role.getNome().getDecricao();
    }
}
