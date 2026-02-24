package com.webone.quiosq.core.queryfilter;

import static com.webone.quiosq.core.FuncionarioSpec.cpfContains;
import static com.webone.quiosq.core.FuncionarioSpec.equalQuiosqueId;
import static com.webone.quiosq.core.FuncionarioSpec.nomeContains;
import static com.webone.quiosq.core.FuncionarioSpec.roleNameEquals;
import static com.webone.quiosq.core.FuncionarioSpec.userIdNotEquals;

import com.webone.quiosq.entity.User;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class FuncionarioQueryFilter {

    private String search;

    public Specification<User> toSpecification(UUID userID, UUID quiosqueID) {
        return (roleNameEquals()).and(
            nomeContains(search).or(cpfContains(search))
                .and(equalQuiosqueId(quiosqueID)).and(userIdNotEquals(userID)));
    }
}
