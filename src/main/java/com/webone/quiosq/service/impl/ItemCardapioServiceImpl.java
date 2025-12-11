package com.webone.quiosq.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webone.quiosq.controller.response.ItemCardapioResponse;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.ItemCardapio;
import com.webone.quiosq.repository.ItemCardapioRepository;
import com.webone.quiosq.service.ItemCardapioService;
import java.util.UUID;
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

    @Override
    public PageableDto<ItemCardapioResponse> findAllByPageableSpec(
        Specification<ItemCardapio> spec,
        Integer page, Integer size, String orderBy, String direction) {
        final var pageRequest = PageRequest.of(page, size,
            Sort.by(Sort.Direction.valueOf(direction), orderBy));
        Page<ItemCardapioResponse> maplis = itemCardapioRepository.findAll( spec,
                pageRequest)
            .map(ItemCardapioResponse::new);
        return new PageableDto<>(maplis);
    }
}
