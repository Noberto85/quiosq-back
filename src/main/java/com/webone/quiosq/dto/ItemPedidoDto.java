package com.webone.quiosq.dto;

import com.webone.quiosq.entity.ItemPedido;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ItemPedidoDto {

    private Long id;
    private Integer quantidade;
    private BigDecimal valorSoma;
    private String nome;
    private BigDecimal preco;

    public ItemPedidoDto(ItemPedido ip) {
        this.id = ip.getId();
        this.quantidade = ip.getQuantidade();
        this.valorSoma = ip.getValorSoma();
        this.nome = ip.getItemCardapio().getNome();
        this.preco = ip.getItemCardapio().getPreco();
    }
}
