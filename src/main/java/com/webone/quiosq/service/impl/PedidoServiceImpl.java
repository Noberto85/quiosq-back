package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.dto.ItemDTO;
import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.exception.CodeErro.RoleError;
import com.webone.quiosq.exception.SqlException;
import com.webone.quiosq.repository.ItemPedidoRepository;
import com.webone.quiosq.repository.PedidoRepository;
import com.webone.quiosq.service.PedidoService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository;
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    public void createPedido(PedidoRequest request) {
        String arrayPg = buildItensList(request.getItems());

        try {
            pedidoRepository.createPedido(request.getQuiosqueId(), request.getMesa(),
                request.getClienteId(), arrayPg);
        } catch (Exception e) {
            throw new SqlException(RoleError.PERFIL_NAO_ENCONTRADO.getCodeErro(), e.getMessage());
        }
    }

    @Override
    public List<PedidoResponse> findPedido(UUID quisoqueID, Integer mesa) {
        List<Pedido> pedido = pedidoRepository.findPedido(quisoqueID,mesa);
        return pedido.stream().map(PedidoResponse::new).collect(Collectors.toList());
    }

    private static String buildItensList(List<ItemDTO> itens) {
        return itens.stream()
            .map(i -> "\"(" + i.getId() + "," + i.getQuantidade() + ")\"")
            .collect(Collectors.joining(",", "{", "}"));
    }
}
