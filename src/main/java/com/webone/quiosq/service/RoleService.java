package com.webone.quiosq.service;

import com.webone.quiosq.dto.RoleDto;
import com.webone.quiosq.entity.Role;
import com.webone.quiosq.entity.enums.RoleName;
import java.util.List;

public interface RoleService {
    Role findByRoleName(RoleName name);

    List<RoleDto> listAllNotgEquals(List<RoleName> names);
}
