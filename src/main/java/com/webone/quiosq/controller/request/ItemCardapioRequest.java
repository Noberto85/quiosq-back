package com.webone.quiosq.controller.request;


import java.math.BigDecimal;

public record ItemCardapioRequest(
    Long id,

    String nome,

    String descricao,

    BigDecimal preco,

    byte[] imagem,

    Long categoriaId
) {


}
