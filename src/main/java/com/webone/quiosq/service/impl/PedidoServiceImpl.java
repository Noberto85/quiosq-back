package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.repository.PedidoRepository;
import com.webone.quiosq.service.PedidoService;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private PedidoRepository pedidoRepository;
    @Override
    public void createPedido(PedidoRequest request) {
        String arrayPg = request.getItems().stream()
            .map(i -> "\"(" + i.getItemId() + "," + i.getQuantidade() + ")\"")
            .collect(Collectors.joining(",", "{", "}"));

        System.out.println(arrayPg);
        pedidoRepository.createPedido(request.getQuiosqueId(), request.getMesa(),request.getClienteId(),arrayPg);


    }
}
