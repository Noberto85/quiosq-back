package com.webone.quiosq.core.queryfilter;


import static com.webone.quiosq.core.GarcomSpec.cpfContains;
import static com.webone.quiosq.core.GarcomSpec.equalQuiosqueId;
import static com.webone.quiosq.core.GarcomSpec.nomeContains;

import com.webone.quiosq.entity.Garcom;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class GarcomQueryFilter {
    private String search;

    public Specification<Garcom> toSpecification(UUID quiosqueID) {
        return (nomeContains(search).or(cpfContains(search))
            .and(equalQuiosqueId(quiosqueID)));
    }
}
