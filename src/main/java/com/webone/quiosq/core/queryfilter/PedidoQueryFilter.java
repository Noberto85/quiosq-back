package com.webone.quiosq.core.queryfilter;

import static com.webone.quiosq.core.PedidoSpec.codigoContains;
import static com.webone.quiosq.core.PedidoSpec.equalQuiosqueId;
import static com.webone.quiosq.core.PedidoSpec.nomePedido;
import static com.webone.quiosq.core.PedidoSpec.status;

import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class PedidoQueryFilter {

    private StatusPedidoEnum status;
    private String search;

    public Specification<Pedido> toSpecification(UUID quiosqueID) {
        return (codigoContains(search).or(status(status).or(nomePedido(search))))
            .and(equalQuiosqueId(quiosqueID));
    }
}
