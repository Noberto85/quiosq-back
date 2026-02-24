package com.webone.quiosq.core.queryfilter;

import static com.webone.quiosq.core.PedidoSpec.codigoContains;
import static com.webone.quiosq.core.PedidoSpec.equalQuiosqueId;
import static com.webone.quiosq.core.PedidoSpec.numeroMesaEquals;
import static com.webone.quiosq.core.PedidoSpec.statusIn;

import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class FuncQueryFilter {

    private String codigo;

    public Specification<Pedido> toSpecification(UUID quiosqueID, StatusPedidoEnum statusEnum,
        String numeroMesas) {
        return equalQuiosqueId(quiosqueID)
            .and(statusIn(statusEnum))
            .and(numeroMesaEquals(numeroMesas))
            .and(codigoContains(codigo));
    }
}
