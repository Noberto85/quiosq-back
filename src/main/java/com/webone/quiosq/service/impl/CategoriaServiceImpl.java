package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.request.CategoriaRequest;
import com.webone.quiosq.dto.CategoriaDto;
import com.webone.quiosq.entity.Categoria;
import com.webone.quiosq.exception.CodeErro.CategoriaError;
import com.webone.quiosq.exception.CodeErro.QuiosqueError;
import com.webone.quiosq.exception.CodeErro.SqlError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.exception.QuiosqueException;
import com.webone.quiosq.exception.SqlException;
import com.webone.quiosq.repository.CategoriaRepository;
import com.webone.quiosq.repository.QuiosqueRepository;
import com.webone.quiosq.service.CategoriaService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;
    private final ObjectMapper mapper;
    private final QuiosqueRepository quiosqueRepository;

    @Override
    public List<CategoriaDto> findAll(UUID quiosqueId) {
        return repository.findAllByQuiosqueIdOrderByDescricaoAsc(quiosqueId).stream()
            .map(CategoriaDto::new).collect(
                Collectors.toList());
    }

    @Override
    public void createClient(CategoriaRequest request, UUID quiosqueId) {
        final var quiosque = quiosqueRepository.findById(quiosqueId)
            .orElseThrow(() -> new QuiosqueException(
                QuiosqueError.QUIOSQUE_NAO_ENCONTRADO.getCodeErro()));
        final Categoria categoria = mapper.convertValue(request, Categoria.class);
        categoria.setQuiosque(quiosque);
        repository.save(categoria);

    }

    @Override
    public void update(CategoriaRequest request) {
        final var categoria = repository.findById(request.id())
            .orElseThrow(() -> new NotFoundException(
                CategoriaError.CATEGORIA_NAO_ENCONTRADO_ERROR.getCodeErro()));
        categoria.setDescricao(request.descricao());
        repository.save(categoria);
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new SqlException(SqlError.DATA_INTEGRITY_VIOLATION_ERROR.getCodeErro());
        }

    }
}
