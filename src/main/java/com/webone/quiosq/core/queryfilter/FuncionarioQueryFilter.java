package com.webone.quiosq.core.queryfilter;

import static com.webone.quiosq.core.FuncionarioSpec.cpfContains;
import static com.webone.quiosq.core.FuncionarioSpec.equalQuiosqueId;
import static com.webone.quiosq.core.FuncionarioSpec.nomeContains;
import static com.webone.quiosq.core.FuncionarioSpec.roleNameEquals;

import com.webone.quiosq.entity.User;
import com.webone.quiosq.entity.enums.RoleName;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class FuncionarioQueryFilter {
    private String search;

    public Specification<User> toSpecification(UUID quiosqueID) {
        return (roleNameEquals(RoleName.ROLE_GARCOM)).and(nomeContains(search).or(cpfContains(search))
            .and(equalQuiosqueId(quiosqueID)));
    }
}
