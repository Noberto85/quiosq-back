package com.webone.quiosq.service;

import com.webone.quiosq.controller.request.GarcomRequest;
import com.webone.quiosq.controller.response.GarcomResponse;
import com.webone.quiosq.controller.response.GarcomSelectResponse;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.Garcom;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public interface GarcomService {

    void create(GarcomRequest request, UUID quiosqueID);

    PageableDto<GarcomResponse> findAllByPageableSpec(Specification<Garcom> spec,
        Integer page, Integer size,
        String orderBy,
        String direction);

    void disable(Long novoGarcom, Long id);

    List<GarcomSelectResponse> findAllNOtEqualsId( UUID quiosqueId,Long id);

    void activate(Long id);

    void edit(Long id, String nome);

}
