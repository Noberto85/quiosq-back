package com.webone.quiosq.core.queryfilter;


import static com.webone.quiosq.core.ItemCardapioSpec.equalCategoria;
import static com.webone.quiosq.core.ItemCardapioSpec.equalQuiosqueId;
import static com.webone.quiosq.core.ItemCardapioSpec.nomeContains;

import com.webone.quiosq.entity.ItemCardapio;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class ItemCardapioQueryFilter {

    private String nome;
    private String categoria;

    public Specification<ItemCardapio> toSpecification(UUID quiosqueID) {
        return nomeContains(nome)
            .and(equalQuiosqueId(quiosqueID)).and(equalCategoria(categoria));
    }
}
