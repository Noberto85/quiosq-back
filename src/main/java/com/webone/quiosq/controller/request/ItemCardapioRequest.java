package com.webone.quiosq.controller.request;


import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class ItemCardapioRequest {


    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private String urlImagem;

    private Integer avaliacao;

    private Long categoriaId;

    private UUID quiosqueId;


}
