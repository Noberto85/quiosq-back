package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.request.ClienteRequest;
import com.webone.quiosq.controller.response.ClienteResponse;
import com.webone.quiosq.dto.ClientDetails;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;
import com.webone.quiosq.entity.Cliente;
import com.webone.quiosq.entity.ClienteQuiosque;
import com.webone.quiosq.entity.ClienteQuiosqueId;
import com.webone.quiosq.entity.Quiosque;
import com.webone.quiosq.entity.enums.RoleName;
import com.webone.quiosq.repository.ClienteQuiosqueRepository;
import com.webone.quiosq.repository.ClienteRepository;
import com.webone.quiosq.service.AuthService;
import com.webone.quiosq.service.ClienteService;
import com.webone.quiosq.service.QuiosqueService;
import com.webone.quiosq.service.RoleService;
import com.webone.quiosq.service.SystemRoleService;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ClienteQuiosqueRepository clienteQuiosqueRepository;
    private final QuiosqueService quiosqueService;
    private final ObjectMapper mapper;
    private final RoleService roleService;
    private final AuthService authService;
    private final SystemRoleService systemRoleService;

    @Override
    public RecoveryJwtTokenDto save(ClienteRequest request) {

        Cliente cliente = null;
        Optional<Cliente> optCliente = repository.findByTelefone(
            request.getTelefone());
        var quiosque = quiosqueService.findByIdOpt(request.getQuiosqueId());
        if (optCliente.isEmpty()) {

            var role = roleService.findByRoleName(RoleName.ROLE_CLIENTE);
            cliente = mapper.convertValue(request, Cliente.class);
            cliente.setQuiosques(Set.of(quiosque));
            cliente.setRole(role);

        }

        if (optCliente.isPresent()) {
            cliente = optCliente.get();
            Optional<Quiosque> quiosqueOpt = cliente.getQuiosques().stream()
                .filter(qu -> qu.getId().equals(request.getQuiosqueId()))
                .findFirst();
            if (quiosqueOpt.isEmpty()) {
                cliente.getQuiosques().add(quiosque);
            }
            cliente.setUltimoAcesso(LocalDateTime.now());
        }

        repository.save(cliente);

        var cq = ClienteQuiosque.builder().
            id(new ClienteQuiosqueId(cliente.getId(), quiosque.getId())).
            cliente(cliente).
            quiosque(quiosque).
            dataUltimoAcesso(LocalDateTime.now()).
            build();
        cq.setDataUltimoAcesso(LocalDateTime.now());

        clienteQuiosqueRepository.save(cq);
        ClientDetails build = ClientDetails.builder()
            .clienteId(cliente.getId())
            .telefone(cliente.getTelefone())
            .quiosqueId(request.getQuiosqueId())
            .mesa(request.getMesa())
            .taxa(systemRoleService.getTaxa())
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
