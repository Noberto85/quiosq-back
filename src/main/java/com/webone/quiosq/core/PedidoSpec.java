package com.webone.quiosq.core;

import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

public class PedidoSpec {

    private static final String STATUS = "status";
    private static final String CODIGO = "codigo";
    private static final String QUIOSQUE = "quiosque";
    private static final String MESA = "mesa";
    public static final String NUMERO = "numero";

    public static Specification<Pedido> codigoContains(String codigo) {

        return (root, query, builder) -> {

            if (ObjectUtils.isEmpty(codigo)) {
                return null;
            }
            return builder.like(builder.lower(root.get(CODIGO)), codigo);
        };
    }


    public static Specification<Pedido> equalQuiosqueId(UUID quiosqueId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(quiosqueId)) {
                return null;
            }

            root.fetch("cliente", JoinType.LEFT);
            root.fetch("garcom", JoinType.LEFT);
            root.fetch("mesa", JoinType.LEFT);
            root.fetch("itens", JoinType.LEFT)
                .fetch("itemCardapio", JoinType.LEFT);

            query.distinct(true); // evita duplicados

            return builder.equal(root.get(QUIOSQUE).get("id"), quiosqueId);
        };
    }

    public static Specification<Pedido> status(StatusPedidoEnum status) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(status)) {
                return null;
            }
            return builder.equal(root.get(STATUS), status);
        };
    }

    public static Specification<Pedido> statusIn(StatusPedidoEnum status) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(status)) {

                return root.get(STATUS)
                    .in(List.of(StatusPedidoEnum.EM_ENTREGA,StatusPedidoEnum.ENTREGUE, StatusPedidoEnum.EM_PREPARACAO));
            }

            return builder.equal(root.get(STATUS), status);

        };
    }

    public static Specification<Pedido> numeroMesaEquals(String numeroMesas) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(numeroMesas)) {
                return null;
            }
            return builder.equal(root.get(MESA).get(NUMERO), numeroMesas);
        };
    }
}
