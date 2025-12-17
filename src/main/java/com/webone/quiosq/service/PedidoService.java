package com.webone.quiosq.service;


import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.controller.response.PedidoResponse;
import java.util.List;
import java.util.UUID;

public interface PedidoService {

    void createPedido(PedidoRequest request);

    List<PedidoResponse> findPedido(UUID quisoqueID, Integer mesa);

}
