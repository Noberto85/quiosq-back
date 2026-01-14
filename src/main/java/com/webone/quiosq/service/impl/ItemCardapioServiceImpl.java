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
import com.webone.quiosq.exception.CodeErro.QuiosqueError;
import com.webone.quiosq.exception.QuiosqueException;
import com.webone.quiosq.repository.CategoriaRepository;
import com.webone.quiosq.repository.ItemCardapioRepository;
import com.webone.quiosq.repository.QuiosqueRepository;
import com.webone.quiosq.service.ItemCardapioService;
import lombok.AllArgsConstructor;
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
    public void create(ItemCardapioRequest request) {
        final ItemCardapio itemCardapio = mapper.convertValue(request, ItemCardapio.class);
        Quiosque quiosque = quiosqueRepository.findById(request.getQuiosqueId())
            .orElseThrow(() -> new QuiosqueException(
                QuiosqueError.QUIOSQUE_NAO_ENCONTRADO.getCodeErro()));
        final Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
            .orElseThrow(() -> new CategoriaException(
                CategoriaError.CATEGORIA_NAO_ENCONTRADO_ERROR.getCodeErro()));
        itemCardapio.setCategoria(categoria);
        itemCardapio.setQuiosque(quiosque);
        itemCardapioRepository.save(itemCardapio);
    }
}
