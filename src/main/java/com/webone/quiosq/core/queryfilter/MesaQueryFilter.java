package com.webone.quiosq.core.queryfilter;


import static com.webone.quiosq.core.MesaSpec.equalQuiosqueId;
import static com.webone.quiosq.core.MesaSpec.numero;

import com.webone.quiosq.core.MesaSpec;
import com.webone.quiosq.entity.Mesa;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class MesaQueryFilter {

    private String numero;
    private Boolean ativo;

    public Specification<Mesa> toSpecification(UUID quiosqueID) {
        return (numero(numero))
            .and(equalQuiosqueId(quiosqueID).and(MesaSpec.colAtivo(ativo)));
    }
}
