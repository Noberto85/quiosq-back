package com.webone.quiosq.service;


import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.itg.response.PagamentoApiResponse;
import java.util.List;
import java.util.UUID;

public interface PedidoService {

    PagamentoApiResponse createPedido(PedidoRequest request);

    List<PedidoResponse> findPedido(UUID quisoqueID, Integer mesa);

}
