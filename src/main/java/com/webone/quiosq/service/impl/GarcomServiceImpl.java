package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.request.GarcomRequest;
import com.webone.quiosq.controller.response.GarcomResponse;
import com.webone.quiosq.controller.response.GarcomSelectResponse;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.Garcom;
import com.webone.quiosq.entity.Mesa;
import com.webone.quiosq.entity.Quiosque;
import com.webone.quiosq.exception.CodeErro.GeralError;
import com.webone.quiosq.exception.GarcomException;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.repository.GarcomRepository;
import com.webone.quiosq.service.GarcomService;
import com.webone.quiosq.service.MesaService;
import com.webone.quiosq.service.QuiosqueService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GarcomServiceImpl implements GarcomService {

    private final GarcomRepository repository;
    private final QuiosqueService service;
    private final ObjectMapper mapper;
    private final MesaService mesaService;

    @Override
    public void create(GarcomRequest request, UUID quiosqueId) {
        final Quiosque quiosque = service.findByIdOpt(quiosqueId);
        var garcomOpt = repository.findByCpfAndQuiosqueId(request.getCpf(),
            quiosqueId);

        if (garcomOpt.isPresent()) {
            throw new GarcomException(GeralError.JA_ESTA_CADASTRADO.getCodeErro());
        }

        var garcom = mapper.convertValue(request, Garcom.class);
        garcom.setQuiosque(quiosque);
        repository.save(garcom);

    }

    @Override
    public PageableDto<GarcomResponse> findAllByPageableSpec(Specification<Garcom> spec,
        Integer page, Integer size, String orderBy, String direction) {
        final var pageRequest = PageRequest.of(page, size,
            Sort.by(Sort.Direction.valueOf(direction), orderBy));
        Page<GarcomResponse> maplis = repository.findAll(spec, pageRequest)
            .map(GarcomResponse::new);
        return new PageableDto<>(maplis);
    }

    @Override
    public void disable(Long novoGarcom, Long id) {
        var garcom = findById(id);
        garcom.setAtivo(Boolean.FALSE);
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
    public List<GarcomSelectResponse> findAllNOtEqualsId(UUID quiosqueId, Long id) {
        return repository.findAllByOrderByNomeAsc(quiosqueId, id).stream()
            .map(GarcomSelectResponse::new)
            .collect(
                Collectors.toList());
    }

    @Override
    public void activate(Long id) {
        final Garcom garcom = findById(id);
        garcom.setAtivo(Boolean.TRUE);
        repository.save(garcom);
    }

    @Override
    public void edit(Long id, String nome) {
        final Garcom garcom = findById(id);
        garcom.setNome(nome);
        repository.save(garcom);
    }

    private Garcom findByCpf(String cpf, UUID quiosqueId) {
        return repository.findByCpfAndQuiosqueId(cpf, quiosqueId)
            .orElseThrow(() -> new NotFoundException(
                GeralError.NAO_ENCONTRADO.getCodeErro()));
    }

    private Garcom findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(GeralError.NAO_ENCONTRADO.getCodeErro()));
    }
}
