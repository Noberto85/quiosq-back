package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.request.FuncionarioCreateRequest;
import com.webone.quiosq.controller.response.GarcomResponse;
import com.webone.quiosq.controller.response.GarcomSelectResponse;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.Mesa;
import com.webone.quiosq.entity.User;
import com.webone.quiosq.entity.enums.RoleName;
import com.webone.quiosq.exception.CodeErro.GeralError;
import com.webone.quiosq.exception.CodeErro.NaoAutorizadoError;
import com.webone.quiosq.exception.CodeErro.QuiosqueError;
import com.webone.quiosq.exception.CodeErro.RoleError;
import com.webone.quiosq.exception.CodeErro.UserError;
import com.webone.quiosq.exception.NaoAutorizadoException;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.exception.QuiosqueException;
import com.webone.quiosq.repository.MesaRepository;
import com.webone.quiosq.repository.QuiosqueRepository;
import com.webone.quiosq.repository.RoleRepository;
import com.webone.quiosq.repository.UserRepository;
import com.webone.quiosq.service.FuncionarioService;
import com.webone.quiosq.service.MesaService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final QuiosqueRepository quiosqueRepository;
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final MesaService mesaService;

    @Override
    public void create(FuncionarioCreateRequest request, UUID quiosqueId) {
        var quiosque = quiosqueRepository.findById(quiosqueId)
            .orElseThrow(() -> new QuiosqueException(
                QuiosqueError.QUIOSQUE_NAO_ENCONTRADO.getCodeErro()));

        final var role = roleRepository.findByNome(request.role())
            .orElseThrow(() -> new NotFoundException(
                RoleError.PERFIL_NAO_ENCONTRADO.getCodeErro()));

        if (role.getNome().equals(RoleName.ROLE_SYSTEM_ADMIN) || role.getNome()
            .equals(RoleName.ROLE_CLIENTE)) {
            throw new NaoAutorizadoException(NaoAutorizadoError.ROLE_NAO_AUTORIZADO.getCodeErro());
        }
        final Optional<User> userOpt = repository.findByCpfAndActive(request.cpf(), true);
        if (userOpt.isPresent()) {
            throw new NotFoundException(
                UserError.USUARIO_JA_CADASTRADO.getCodeErro());
        }
        final User user = mapper.convertValue(request, User.class);
        String senhaTemp = String.format("%s%s", request.cpf().substring(0, 3),
            request.telefone().substring(0, 3));
        user.setRoles(List.of(role));
        user.setQuiosque(quiosque);
        user.setPassword(passwordEncoder.encode(senhaTemp));
        repository.save(user);
    }

    @Override
    public PageableDto<GarcomResponse> findAllByPageableSpec(Specification<User> spec, Integer page,
        Integer size, String orderBy, String direction) {
        final var pageRequest = PageRequest.of(page, size,
            Sort.by(Sort.Direction.valueOf(direction), orderBy));
        Page<GarcomResponse> maplis = repository.findAll(spec, pageRequest)
            .map(GarcomResponse::new);
        return new PageableDto<>(maplis);
    }

    @Override
    public void disable(UUID novoGarcom, UUID id) {
        var garcom = findById(id);
        garcom.setActive(Boolean.FALSE);
        var upNovoGarcom = findById(novoGarcom);
        final List<Mesa> mesaList = mesaService.findByGarcom(garcom).stream()
            .peek(mesa -> mesa.setGarcom(upNovoGarcom)
            ).toList();
        if (!mesaList.isEmpty()) {
            mesaService.salvar(mesaList);

        }
        repository.save(garcom);
    }


    @Override
    public List<GarcomSelectResponse> findAllNOtEqualsId(UUID quiosqueId, UUID id) {
        return repository.findAllByOrderByNomeAsc(quiosqueId, id,RoleName.ROLE_GARCOM).stream()
            .map(GarcomSelectResponse::new)
            .collect(
                Collectors.toList());
    }

    @Override
    public void activate(UUID id) {
        final User user = findById(id);
        user.setActive(Boolean.TRUE);
        repository.save(user);
    }

    @Override
    public void edit(UUID id, String nome) {
        final User user = findById(id);
        user.setNome(nome);
        repository.save(user);
    }

    @Override
    public List<GarcomSelectResponse> findAllWithStatusTrue(UUID quiosqueId) {
        return repository.findAllWithStatusTrue(quiosqueId, RoleName.ROLE_GARCOM).stream()
            .map(GarcomSelectResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public User findById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(GeralError.NAO_ENCONTRADO.getCodeErro()));
    }
}
