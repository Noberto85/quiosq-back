package com.webone.quiosq.controller.response;

import com.webone.quiosq.dto.ItemPedidoDto;
import com.webone.quiosq.entity.ItemPedido;
import com.webone.quiosq.entity.Pedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;


@Data
public class PedidoResponse {

    private static final BigDecimal ACRESCIMO = new BigDecimal("5");

    private Long id;
    private String status;
    private String codigo;
    private Integer mesa;
    private List<ItemPedidoDto> itens;
    private LocalDateTime dataInit;
    private LocalDateTime dataFim;
    private BigDecimal total;

    public PedidoResponse(Pedido pedido) {
        this.id = pedido.getId();
        this.status = pedido.getStatus().getDescricao();
        this.codigo = pedido.getCodigo();
        this.mesa = pedido.getMesa().getNumero();
        this.itens = pedido.getItens().stream().map(ItemPedidoDto::new)
            .collect(Collectors.toList());
        this.dataInit = pedido.getDataInit();
        this.dataFim = pedido.getDataFim();
        var parcial = pedido.getItens().stream().map(ItemPedido::getValorSoma)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        total = parcial.add(ACRESCIMO);
    }
}

