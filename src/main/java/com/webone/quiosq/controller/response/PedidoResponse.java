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
    private String nomePedido;
    private String garcom;
    private String codigo;
    private String mesa;
    private List<ItemPedidoDto> itens;
    private LocalDateTime dataInit;
    private LocalDateTime dataFim;
    private LocalDateTime dataContagem;
    private BigDecimal total;
    private String cliente;
    private String observacoes;

    public PedidoResponse(Pedido pedido) {
        this.id = pedido.getId();
        this.nomePedido = pedido.getNomePedido();
        this.garcom = pedido.getGarcom().getNome();
        this.status = pedido.getStatus().getDescricao();
        this.codigo = pedido.getCodigo();
        this.mesa = (pedido.getMesa().getNumero() <= 9) ? "0" + pedido.getMesa().getNumero()
            : pedido.getMesa().getNumero().toString();
        this.cliente = pedido.getCliente().getTelefone();
        this.itens = pedido.getItens().stream().map(ItemPedidoDto::new)
            .collect(Collectors.toList());
        this.dataInit = pedido.getDataInit();
        this.dataContagem = pedido.getDataContagem();
        this.dataFim = pedido.getDataFim();
        total = pedido.getItens().stream().map(ItemPedido::getValorSoma)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}

