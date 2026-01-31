package com.webone.quiosq.service.impl;

import com.webone.quiosq.dto.RoleDto;
import com.webone.quiosq.entity.Role;
import com.webone.quiosq.entity.enums.RoleName;
import com.webone.quiosq.exception.CodeErro.RoleError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.repository.RoleRepository;
import com.webone.quiosq.service.RoleService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role findByRoleName(RoleName name) {
        return repository.findByNome(name).orElseThrow(
            () -> new NotFoundException(RoleError.PERFIL_NAO_ENCONTRADO.getCodeErro()));
    }

    @Override
    public List<RoleDto> listAllNotgEquals(List<RoleName> names) {
        return repository.listAllNotEquals(names).stream().map(RoleDto::new).toList();
    }
}
