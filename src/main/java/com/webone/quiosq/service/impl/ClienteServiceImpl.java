package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.request.ClienteRequest;
import com.webone.quiosq.controller.response.ClienteResponse;
import com.webone.quiosq.dto.ClientDetails;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;
import com.webone.quiosq.entity.Cliente;
import com.webone.quiosq.entity.enums.RoleName;
import com.webone.quiosq.repository.ClienteRepository;
import com.webone.quiosq.service.AuthService;
import com.webone.quiosq.service.ClienteService;
import com.webone.quiosq.service.QuiosqueService;
import com.webone.quiosq.service.RoleService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final QuiosqueService quiosqueService;
    private final ObjectMapper mapper;
    private final RoleService roleService;
    private final AuthService authService;

    @Override
    public RecoveryJwtTokenDto save(ClienteRequest request) {
        Cliente cliente;
        Optional<Cliente> optCliente = repository.findByTelefoneAndQuiosqueId(
            request.getTelefone(), request.getQuiosqueId());
        if (optCliente.isEmpty()) {
            var quiosque = quiosqueService.findByIdOpt(request.getQuiosqueId());
            var role = roleService.findByRoleName(RoleName.ROLE_CLIENTE);
            cliente = mapper.convertValue(request, Cliente.class);
            cliente.setQuiosque(quiosque);
            cliente.setRole(role);
        } else {
            cliente = optCliente.get();
            cliente.setUltimoAcesso(LocalDateTime.now());

        }
        repository.save(cliente);
        ClientDetails build = ClientDetails.builder()
            .quiosqueId(cliente.getQuiosque().getId())
            .telefone(cliente.getTelefone())
            .nome(cliente.getNome())
            .mesa(request.getMesa())
            .build();
       return authService.authenticateClient(build);

    }

    @Override
    public ClienteResponse findByTelefoneAndQuiosqueId(String telefone, UUID quiosqueId) {
        return null;
    }

    @Override
    public Cliente findByTelefoneAndQuiosqueIdOpt(String telefone, UUID quiosqueId) {
        return null;
    }
}
