package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.request.ClienteCreateRequest;
import com.webone.quiosq.entity.Cliente;
import com.webone.quiosq.entity.enums.RoleName;
import com.webone.quiosq.exception.ClienteException;
import com.webone.quiosq.exception.CodeErro.ClienteError;
import com.webone.quiosq.repository.ClienteQuiosqueRepository;
import com.webone.quiosq.repository.ClienteRepository;
import com.webone.quiosq.service.AuthService;
import com.webone.quiosq.service.ClienteService;
import com.webone.quiosq.service.QuiosqueService;
import com.webone.quiosq.service.RoleService;
import com.webone.quiosq.service.SystemRoleService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ObjectMapper mapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createCliente(ClienteCreateRequest request) {
        final Optional<Cliente> clienteOpt = repository.findByTelefone(
            request.getTelefone());
        if (clienteOpt.isPresent()) {
            throw new ClienteException(ClienteError.CLIENTE_JA_CADASTRADO.getCodeErro());
        }
        var cliente = mapper.convertValue(request, Cliente.class);
        cliente.setRole(roleService.findByRoleName(RoleName.ROLE_CLIENTE));
        cliente.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(cliente);
    }
}
