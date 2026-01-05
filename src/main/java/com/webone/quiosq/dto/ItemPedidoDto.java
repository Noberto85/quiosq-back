package com.webone.quiosq.dto;

import com.webone.quiosq.entity.ItemPedido;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ItemPedidoDto {

    private Long id;
    private Integer quantidade;
    private BigDecimal valorSoma;
    private String descricao;
    private BigDecimal preco;
    private String categoria;
    private byte[] imagem;
    private Integer avaliacao;

    public ItemPedidoDto(ItemPedido ip) {
        this.id = ip.getId();
        this.quantidade = ip.getQuantidade();
        this.valorSoma = ip.getValorSoma();
        this.descricao = ip.getItemCardapio().getNome();
        this.preco = ip.getItemCardapio().getPreco();
        this.categoria = ip.getItemCardapio().getCategoria().getDescricao();
        this.imagem = ip.getItemCardapio().getImagem();
        this.avaliacao = ip.getItemCardapio().getAvaliacao();
    }
}
