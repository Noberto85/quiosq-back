package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.dto.ItemDTO;
import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.exception.CodeErro.RoleError;
import com.webone.quiosq.exception.SqlException;
import com.webone.quiosq.handler.PagamentoHandle;
import com.webone.quiosq.projection.PedidoProjection;
import com.webone.quiosq.repository.PedidoRepository;
import com.webone.quiosq.service.MercadoPagoTokenService;
import com.webone.quiosq.service.PedidoService;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final List<PagamentoHandle> pagamentoHandle;
    private PedidoRepository pedidoRepository;
    private MercadoPagoTokenService mercadoPagoTokenService;

    @Override
    public void createPedido(PedidoRequest request) {
        String arrayPg = buildItensList(request.getItems());

        try {

            Optional<PedidoProjection> pedido = pedidoRepository.createPedido(
                request.getQuiosqueId(), request.getMesa(),
                request.getClienteId(), arrayPg);
            if (pedido.isEmpty()) {
                throw new SqlException(RoleError.PERFIL_NAO_ENCONTRADO.getCodeErro(), null);
            }
            request.setPedidoId(pedido.get().getPedidoId());
            request.setCodePedido(pedido.get().getCodigoPedido());
            String accessToken = mercadoPagoTokenService.getAccessToken(request.getQuiosqueId());
            Iterator<PagamentoHandle> iterator = pagamentoHandle.iterator();
            if (iterator.hasNext()) {
                PagamentoHandle next = iterator.next();
                next.handleRequest(request, accessToken);
            }
        } catch (Exception e) {
            throw new SqlException(RoleError.PERFIL_NAO_ENCONTRADO.getCodeErro(), e.getMessage());
        }
    }

    @Override
    public List<PedidoResponse> findPedido(UUID quisoqueID, Integer mesa) {
        List<Pedido> pedido = pedidoRepository.findPedido(quisoqueID, mesa);
        return pedido.stream().map(PedidoResponse::new).collect(Collectors.toList());
    }

    private static String buildItensList(List<ItemDTO> itens) {
        return itens.stream()
            .map(i -> "\"(" + i.getId() + "," + i.getQuantidade() + ")\"")
            .collect(Collectors.joining(",", "{", "}"));
    }
}
