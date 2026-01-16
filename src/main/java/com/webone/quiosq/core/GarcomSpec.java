package com.webone.quiosq.core;

import com.webone.quiosq.entity.Garcom;
import com.webone.quiosq.entity.ItemCardapio;
import jakarta.persistence.criteria.JoinType;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

public class GarcomSpec {

    private static final String NOME = "nome";
    private static final String CPF = "cpf";
    private static final String QUIOSQUE = "quiosque";

    public static Specification<Garcom> nomeContains(String nome) {

        return (root, query, builder) -> {

            if (ObjectUtils.isEmpty(nome)) {
                return null;
            }
            return builder.like(builder.lower(root.get(NOME)), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<Garcom> cpfContains(String cpf) {

        return (root, query, builder) -> {

            if (ObjectUtils.isEmpty(cpf)) {


                return null;
            }
            return builder.like(builder.lower(root.get(CPF)), "%" + cpf.toLowerCase() + "%");
        };
    }

    public static Specification<Garcom> equalQuiosqueId(UUID quiosqueId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(quiosqueId)) {


                return null;
            }

            return builder.equal(root.get(QUIOSQUE).get("id"), quiosqueId);
        };
    }
}
