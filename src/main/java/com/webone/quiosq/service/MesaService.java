package com.webone.quiosq.service;

import com.webone.quiosq.controller.request.MesaQrcodeDownload;
import com.webone.quiosq.controller.request.MesaRequest;
import com.webone.quiosq.controller.response.MesaQrcodeResponse;
import com.webone.quiosq.controller.response.MesaResponse;
import com.webone.quiosq.dto.MesaProjectionDto;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.Garcom;
import com.webone.quiosq.entity.Mesa;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public interface MesaService {

    void salvar(List<Mesa> mesalist);

    MesaProjectionDto buildMesa(UUID quiosqueId, Long mesaId);

    Long findByQuiosqueAndMesa(UUID quiosqueId, Integer numero);

    List<Mesa> findByGarcom(Garcom garcom);

    PageableDto<MesaResponse> findAllByPageableSpec(Specification<Mesa> spec,
        Integer page, Integer size,
        String orderBy,
        String direction);

    void create(MesaRequest request, UUID quiosqueID);

    void update(MesaRequest request);

    void disable(Long id);

    MesaQrcodeResponse download(MesaQrcodeDownload request);
}
