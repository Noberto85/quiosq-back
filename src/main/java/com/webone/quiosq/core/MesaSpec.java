package com.webone.quiosq.core;

import com.webone.quiosq.entity.Mesa;
import com.webone.quiosq.entity.Pedido;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

public class MesaSpec {

    private static final String NUMERO = "numero";
    private static final String QUIOSQUE = "quiosque";

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

}
