package com.webone.quiosq.service;

import com.webone.quiosq.entity.Role;
import com.webone.quiosq.entity.enums.RoleName;

public interface RoleService {
    Role findByRoleName(RoleName name);
}
