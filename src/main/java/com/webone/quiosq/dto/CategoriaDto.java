package com.webone.quiosq.dto;

import com.webone.quiosq.entity.Categoria;
import lombok.Data;

@Data
public class CategoriaDto {

    private Long id;
    private String descricao;

    public CategoriaDto(Categoria categoria) {
        this.id = categoria.getId();
        this.descricao = categoria.getDescricao();
    }
}
