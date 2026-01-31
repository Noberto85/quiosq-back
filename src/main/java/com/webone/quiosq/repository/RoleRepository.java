package com.webone.quiosq.repository;

import com.webone.quiosq.entity.Role;
import com.webone.quiosq.entity.enums.RoleName;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(RoleName name);

    @Query("SELECT r FROM Role r WHERE r.nome NOT IN (:nomes) ORDER BY r.nome")
    List<Role> listAllNotEquals(@Param("nomes") List<RoleName> nomes);
}
