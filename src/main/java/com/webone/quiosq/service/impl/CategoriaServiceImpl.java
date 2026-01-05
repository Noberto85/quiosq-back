package com.webone.quiosq.service.impl;

import com.webone.quiosq.dto.CategoriaDto;
import com.webone.quiosq.repository.CategoriaRepository;
import com.webone.quiosq.service.CategoriaService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;

    @Override
    public List<CategoriaDto> findAll() {
        return repository.findAllByOrderByDescricaoAsc().stream().map(CategoriaDto::new).collect(
            Collectors.toList());
    }
}
