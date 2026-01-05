package com.webone.quiosq.repository;

import com.webone.quiosq.entity.SystemRole;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SystemRoleRepository extends JpaRepository<SystemRole, Long> {

    @Query("SELECT s.taxa FROM SystemRole s ORDER BY s.id DESC LIMIT 1")
    Optional<BigDecimal> getTaxa();
}
