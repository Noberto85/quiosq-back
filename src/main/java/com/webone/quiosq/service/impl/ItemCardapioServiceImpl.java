package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.request.ItemCardapioRequest;
import com.webone.quiosq.controller.response.ItemCardapioResponse;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.Categoria;
import com.webone.quiosq.entity.ItemCardapio;
import com.webone.quiosq.entity.Quiosque;
import com.webone.quiosq.exception.CategoriaException;
import com.webone.quiosq.exception.CodeErro.CategoriaError;
import com.webone.quiosq.exception.CodeErro.GeralError;
import com.webone.quiosq.exception.CodeErro.QuiosqueError;
import com.webone.quiosq.exception.CodeErro.SqlError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.exception.QuiosqueException;
import com.webone.quiosq.exception.SqlException;
import com.webone.quiosq.repository.CategoriaRepository;
import com.webone.quiosq.repository.ItemCardapioRepository;
import com.webone.quiosq.repository.QuiosqueRepository;
import com.webone.quiosq.service.ItemCardapioService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemCardapioServiceImpl implements ItemCardapioService {

    private final ItemCardapioRepository itemCardapioRepository;
    private final ObjectMapper mapper;
    private final QuiosqueRepository quiosqueRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public PageableDto<ItemCardapioResponse> findAllByPageableSpec(
        Specification<ItemCardapio> spec,
        Integer page, Integer size, String orderBy, String direction) {
        final var pageRequest = PageRequest.of(page, size,
            Sort.by(Sort.Direction.valueOf(direction), orderBy));
        Page<ItemCardapioResponse> maplis = itemCardapioRepository.findAll(spec,
                pageRequest)
            .map(ItemCardapioResponse::new);
        return new PageableDto<>(maplis);
    }

    @Override
    public void create(ItemCardapioRequest request, UUID quiosqueId) {
        final ItemCardapio itemCardapio = mapper.convertValue(request, ItemCardapio.class);
        Quiosque quiosque = quiosqueRepository.findById(quiosqueId)
            .orElseThrow(() -> new QuiosqueException(
                QuiosqueError.QUIOSQUE_NAO_ENCONTRADO.getCodeErro()));
        final Categoria categoria = getCategoria(request.id());
        itemCardapio.setCategoria(categoria);
        itemCardapio.setQuiosque(quiosque);
        itemCardapioRepository.save(itemCardapio);
    }

    private Categoria getCategoria(Long id) {
        final Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new CategoriaException(
                CategoriaError.CATEGORIA_NAO_ENCONTRADO_ERROR.getCodeErro()));
        return categoria;
    }

    @Override
    public void update(ItemCardapioRequest request) {
        final ItemCardapio itemCardapio = getItemCardapio(request.id());
        final Categoria categoria = getCategoria(request.categoriaId());
        itemCardapio.setCategoria(categoria);
        itemCardapio.setImagem(request.imagem());
        itemCardapio.setPreco(request.preco());
        itemCardapio.setDescricao(request.descricao());
        itemCardapio.setNome(request.nome());
        itemCardapioRepository.save(itemCardapio);
    }

    private ItemCardapio getItemCardapio(Long id) {
        return itemCardapioRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                GeralError.NAO_ENCONTRADO.getCodeErro()));
    }

    @Override
    public void delete(Long id) {
        try {
            itemCardapioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new SqlException(SqlError.DATA_INTEGRITY_VIOLATION_ERROR.getCodeErro());
        }
    }

    @Override
    public void habilarDesabilitar(Long id, Boolean habilita) {
        final ItemCardapio itemCardapio = getItemCardapio(id);
        if (habilita) {
            itemCardapio.setAtivo(Boolean.FALSE);
        } else {
            itemCardapio.setAtivo(Boolean.TRUE);
        }
        itemCardapioRepository.save(itemCardapio);
    }
}
