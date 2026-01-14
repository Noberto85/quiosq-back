package com.webone.quiosq.core.queryfilter;


import static com.webone.quiosq.core.MesaSpec.equalQuiosqueId;

import com.webone.quiosq.core.MesaSpec;
import com.webone.quiosq.entity.Mesa;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class MesaQueryFilter {

    private String numero;

    public Specification<Mesa> toSpecification(UUID quiosqueID) {
        return (MesaSpec.numero(numero))
            .and(equalQuiosqueId(quiosqueID));
    }
}
