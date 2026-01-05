package com.webone.quiosq.service;


import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.dto.PagamentoResponse;
import java.util.List;
import java.util.UUID;

public interface PedidoService {

    PagamentoResponse createPedido(PedidoRequest request);

    List<PedidoResponse> findPedido(UUID quisoqueID, Integer mesa);

    List<PedidoResponse> findByClienteId(UUID quiosqueId, String telefone);


}
