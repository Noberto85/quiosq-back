package com.webone.quiosq.core;

import com.webone.quiosq.entity.Role;
import com.webone.quiosq.entity.User;
import com.webone.quiosq.entity.enums.RoleName;
import jakarta.persistence.criteria.Join;
import java.util.Arrays;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

public class FuncionarioSpec {

    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String CPF = "cpf";
    private static final String QUIOSQUE = "quiosque";
    private static final String ROLES = "roles";

    public static Specification<User> roleNameEquals() {
        return (root, query, builder) -> {

            if (query.getResultType() != Long.class) {
                root.fetch(ROLES);
                root.fetch(QUIOSQUE);
                query.distinct(true);
            }
            Join<User, Role> rolesJoin = root.join(ROLES);
            return builder.in(rolesJoin.get(NOME)).value(
                Arrays.asList(RoleName.ROLE_GARCOM, RoleName.ROLE_COZINHA, RoleName.ROLE_ADMIN));
        };
    }

    public static Specification<User> nomeContains(String nome) {

        return (root, query, builder) -> {

            if (ObjectUtils.isEmpty(nome)) {
                return null;
            }
            return builder.like(builder.lower(root.get(NOME)), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<User> cpfContains(String cpf) {

        return (root, query, builder) -> {

            if (ObjectUtils.isEmpty(cpf)) {

                return null;
            }
            return builder.like(builder.lower(root.get(CPF)), "%" + cpf.toLowerCase() + "%");
        };
    }

    public static Specification<User> equalQuiosqueId(UUID quiosqueId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(quiosqueId)) {

                return null;
            }

            return builder.equal(root.get(QUIOSQUE).get("id"), quiosqueId);
        };
    }

    public static Specification<User> userIdNotEquals(UUID userId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(userId)) {

                return null;
            }
            return builder.notEqual(root.get(ID), userId);
        };
    }
}
