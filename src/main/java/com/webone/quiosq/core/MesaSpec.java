package com.webone.quiosq.core;

import com.webone.quiosq.entity.Mesa;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

public class MesaSpec {

    private static final String NUMERO = "numero";
    private static final String QUIOSQUE = "quiosque";
    private static final String ATIVO = "ativo";

    public static Specification<Mesa> numero(String numero) {

        return (root, query, builder) -> {

            if (ObjectUtils.isEmpty(numero)) {
                return null;
            }
            return builder.like(builder.lower(root.get(NUMERO)), numero);
        };
    }


    public static Specification<Mesa> equalQuiosqueId(UUID quiosqueId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(quiosqueId)) {
                return null;
            }
            return builder.equal(root.get(QUIOSQUE).get("id"), quiosqueId);
        };
    }

    public static Specification<Mesa> colAtivo(Boolean ativo) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(ativo)) {
                return builder.equal(root.get(ATIVO), Boolean.TRUE);
            }
            return builder.equal(root.get(ATIVO), ativo);
        };
    }

}
