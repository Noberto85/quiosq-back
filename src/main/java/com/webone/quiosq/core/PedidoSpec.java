package com.webone.quiosq.core;

import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import java.util.UUID;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

public class PedidoSpec {

    private static final String NOME_PEDIDO = "nomePedido";
    private static final String STATUS = "status";
    private static final String CODIGO = "codigo";
    private static final String QUIOSQUE = "quiosque";
    private static final String CLIENTE = "cliente";

    public static Specification<Pedido> codigoContains(String codigo) {

        return (root, query, builder) -> {

            if (ObjectUtils.isEmpty(codigo)) {
                return null;
            }
            return builder.like(builder.lower(root.get(CODIGO)), codigo);
        };
    }

    public static Specification<Pedido> nomePedido(String nome) {

        return (root, query, builder) -> {

            if (ObjectUtils.isEmpty(nome)) {
                return null;
            }
            return builder.like(builder.lower(root.get(NOME_PEDIDO)), "%" + nome.toLowerCase() + "%");
        };
    }


    public static Specification<Pedido> equalQuiosqueId(UUID quiosqueId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(quiosqueId)) {
                return null;
            }
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
}
