package com.webone.quiosq.core;

import com.webone.quiosq.entity.ItemCardapio;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

public class ItemCardapioSpec {

    private static final String NOME = "nome";
    private static final String QUIOSQUE = "quiosque";
    private static final String CATEGORIA = "categoria";

    public static Specification<ItemCardapio> nomeContains(String nome) {

        return (root, query, builder) -> {

            if (ObjectUtils.isEmpty(nome)) {

                return null;
            }
            return builder.like(builder.lower(root.get(NOME)), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<ItemCardapio> equalQuiosqueId(UUID quiosqueId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(quiosqueId)) {
                return null;
            }

            return builder.equal(root.get(QUIOSQUE).get("id"), quiosqueId);
        };
    }

    public static Specification<ItemCardapio> equalCategoria(String categoria) {

        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(categoria)) {
                return null;
            }
            return builder.equal(root.get(CATEGORIA).get("descricao"), categoria);
        };
    }


}
