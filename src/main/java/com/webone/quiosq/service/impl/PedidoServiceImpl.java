package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.dto.ItemDTO;
import com.webone.quiosq.exception.CodeErro.RoleError;
import com.webone.quiosq.exception.SqlException;
import com.webone.quiosq.repository.PedidoRepository;
import com.webone.quiosq.service.PedidoService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private PedidoRepository pedidoRepository;

    @Override
    public void createPedido(PedidoRequest request) {
        String arrayPg = buildItensList(request.getItems());

        try {
            pedidoRepository.createPedido(request.getQuiosqueId(), request.getMesa(),
                request.getTelefone(), arrayPg);
        } catch (Exception e) {
            throw new SqlException(RoleError.PERFIL_NAO_ENCONTRADO.getCodeErro(), e.getMessage());
        }


    }

    private static String buildItensList(List<ItemDTO> itens) {
        String arrayPg = itens.stream()
            .map(i -> "\"(" + i.getId() + "," + i.getQuantidade() + ")\"")
            .collect(Collectors.joining(",", "{", "}"));
        return arrayPg;
    }
}
