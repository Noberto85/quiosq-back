package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.request.QuiosqueRequest;
import com.webone.quiosq.controller.response.QuiosqueResponse;
import com.webone.quiosq.dto.CreateUserDto;
import com.webone.quiosq.entity.Categoria;
import com.webone.quiosq.entity.Mesa;
import com.webone.quiosq.entity.Quiosque;
import com.webone.quiosq.entity.enums.RoleName;
import com.webone.quiosq.entity.enums.StatusMesaEnum;
import com.webone.quiosq.exception.CodeErro.GeralError;
import com.webone.quiosq.exception.CodeErro.QuiosqueError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.exception.QuiosqueException;
import com.webone.quiosq.repository.CategoriaRepository;
import com.webone.quiosq.repository.MesaRepository;
import com.webone.quiosq.repository.QuiosqueRepository;
import com.webone.quiosq.service.QuiosqueService;
import com.webone.quiosq.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuiosqueServiceImpl implements QuiosqueService {

    private final QuiosqueRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final MesaRepository mesaRepository;
    private final ObjectMapper mapper;
    private final UserService userService;

    @Override
    @Transactional
    public Quiosque save(QuiosqueRequest request) {
        final Optional<Quiosque> opt = repository.findByCnpj(request.getCnpj());
        if (opt.isPresent()) {
            throw new QuiosqueException(QuiosqueError.DOCUMENTO_EXITE.getCodeErro());
        }

        final Quiosque quiosque = mapper.convertValue(request, Quiosque.class);
        quiosque.setAtivo(Boolean.TRUE);
        repository.save(quiosque  );
        CreateUserDto createUserDto = new CreateUserDto(quiosque.getEmail(), quiosque.getNome(),
            "123456",
            RoleName.ROLE_ADMIN, quiosque.getId());
        userService.createUser(createUserDto);
        cargaInicial(quiosque);
        return quiosque;


    }

    @Override
    public QuiosqueResponse findById(UUID id) {
        var prov = findByIdOpt(id);
        return mapper.convertValue(prov, QuiosqueResponse.class);
    }

    @Override
    public Quiosque findByIdOpt(UUID id) {
        return repository.findById(id).orElseThrow(
            () -> new NotFoundException(GeralError.NAO_ENCONTRADO.getCodeErro()));
    }

    @Override
    public void delete(UUID id) {
        final var byId = findById(id);
        repository.deleteById(byId.getId());
    }

    @Override
    public List<QuiosqueResponse> findAll() {
        List<Quiosque> all = repository.findAll();
        return all.stream()
            .map(p -> mapper.convertValue(p, QuiosqueResponse.class))
            .collect(Collectors.toList());

    }

    private void cargaInicial(Quiosque quiosque) {
        // Criando categorias com Stream
        String[] categorias = {"Bebidas", "Lanches", "Sobremesas", "Porções", "Pratos Feitos",
            "Drinks"};

        List<Categoria> listaCat = Arrays.stream(categorias)
            .map(cat -> Categoria.builder()
                .descricao(cat)
                .quiosque(quiosque)
                .build())
            .toList();

        categoriaRepository.saveAll(listaCat);
    }

}
