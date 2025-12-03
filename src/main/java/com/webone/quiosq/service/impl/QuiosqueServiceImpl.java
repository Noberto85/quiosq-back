package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.request.QuiosqueRequest;
import com.webone.quiosq.controller.response.QuiosqueResponse;
import com.webone.quiosq.entity.Quiosque;
import com.webone.quiosq.exception.CodeErro.ProvedorError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.repository.QuiosqueRepository;
import com.webone.quiosq.service.QuiosqueService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuiosqueServiceImpl implements QuiosqueService {

    private final QuiosqueRepository repository;
    private final ObjectMapper mapper;

    @Override
    public void save(QuiosqueRequest request) {
        repository.save(mapper.convertValue(request, Quiosque.class));
    }

    @Override
    public QuiosqueResponse findById(Long id) {
        var prov = findByIdOpt(id);
        return mapper.convertValue(prov, QuiosqueResponse.class);
    }

    @Override
    public Quiosque findByIdOpt(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new NotFoundException(ProvedorError.QUIOSQUE_NAO_ENCONTRADO.getCodeErro()));
    }

    @Override
    public void delete(Long id) {
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

}
