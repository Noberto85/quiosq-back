package com.webone.quiosq.service;

import com.webone.quiosq.controller.request.FuncionarioCreateRequest;
import com.webone.quiosq.controller.response.GarcomResponse;
import com.webone.quiosq.controller.response.GarcomSelectResponse;
import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public interface FuncionarioService {

    void create(FuncionarioCreateRequest request, UUID quiosqueId);

    PageableDto<GarcomResponse> findAllByPageableSpec(Specification<User> spec,
        Integer page, Integer size,
        String orderBy,
        String direction);


    void disable(UUID novoGarcom, UUID id);

    void disable(UUID id);

    List<GarcomSelectResponse> findAllNOtEqualsId(UUID quiosqueId, UUID id);

    void activate(UUID id);

    void edit(UUID id, String nome);

    List<GarcomSelectResponse> findAllWithStatusTrue(UUID quiosqueId);

    User findById(UUID id);

    List<PedidoResponse> findAllByStatus(Specification<Pedido> spec);


}
