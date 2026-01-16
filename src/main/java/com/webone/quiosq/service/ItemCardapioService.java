package com.webone.quiosq.service;

import com.webone.quiosq.controller.request.ItemCardapioRequest;
import com.webone.quiosq.controller.response.ItemCardapioResponse;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.ItemCardapio;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public interface ItemCardapioService {

    PageableDto<ItemCardapioResponse> findAllByPageableSpec(Specification<ItemCardapio> spec,
        Integer page, Integer size,
        String orderBy,
        String direction);

    void create(ItemCardapioRequest request, UUID quiosqueId);
    void update(ItemCardapioRequest request);
    void delete(Long id);
    void habilarDesabilitar(Long id, Boolean habilita);
}
