package com.webone.quiosq.core;

import com.webone.quiosq.entity.ItemCardapio;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

public class ItemCardapioSpec {

    private static final String NOME = "nome";
    private static final String QUIOSQUE = "quiosque";

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
            // acessa o campo "quiosque" e dentro dele o "id"
            return builder.equal(root.get(QUIOSQUE).get("id"), quiosqueId);
        };
    }


}
