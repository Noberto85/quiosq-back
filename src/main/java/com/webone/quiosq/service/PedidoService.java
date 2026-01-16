package com.webone.quiosq.service;


import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.controller.response.GarcomResponse;
import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.dto.PagamentoResponse;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.Garcom;
import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public interface PedidoService {

    PagamentoResponse createPedido(PedidoRequest request);

    List<PedidoResponse> findPedido(UUID quisoqueID, Integer mesa);

    List<PedidoResponse> findByClienteId(UUID quiosqueId, String telefone);

    PageableDto<PedidoResponse> findAllByPageableSpec(Specification<Pedido> spec,
        Integer page, Integer size,
        String orderBy,
        String direction);

    void updateStatus(Long id, StatusPedidoEnum status);
}
