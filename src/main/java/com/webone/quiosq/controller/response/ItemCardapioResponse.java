package com.webone.quiosq.controller.response;


import com.webone.quiosq.dto.CategoriaDto;
import com.webone.quiosq.entity.ItemCardapio;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class ItemCardapioResponse {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private byte[] imagem;
    private String urlImagem;
    private Integer avaliacao;
    private String categoria;
    private CategoriaDto categoriaDto;


    public ItemCardapioResponse(ItemCardapio itemCardapio) {
        this.id = itemCardapio.getId();
        this.nome = itemCardapio.getNome();
        this.descricao = itemCardapio.getDescricao();
        this.preco = itemCardapio.getPreco();
        this.imagem = itemCardapio.getImagem();
        this.urlImagem = itemCardapio.getUrlImagem();
        this.avaliacao = itemCardapio.getAvaliacao();
        this.categoria = itemCardapio.getCategoria().getDescricao();
        this.categoriaDto = new CategoriaDto(itemCardapio.getCategoria());
    }
}
