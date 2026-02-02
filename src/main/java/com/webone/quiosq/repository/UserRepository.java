package com.webone.quiosq.repository;


import com.webone.quiosq.entity.User;
import com.webone.quiosq.entity.enums.RoleName;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByCpfAndActive(String cpf, Boolean active);

    @Query("SELECT g FROM User g JOIN g.quiosque q JOIN g.roles r " +
        "WHERE g.active = true AND q.id = :quiosqueId AND r.nome = :roleName ORDER BY g.nome")
    List<User> findAllWithStatusTrue(@Param("quiosqueId") UUID quiosqueId,
        @Param("roleName") RoleName roleName);

    Optional<User> findByCpfAndQuiosqueId(String cpf, UUID quiodqueId);

    @Query("SELECT g FROM User g "
        + "JOIN g.quiosque q JOIN g.roles r  " +
        "WHERE g.id <> :id AND g.active = true AND q.id =:quiosqueId AND r.nome = :roleName " +
        "ORDER BY g.nome ASC")
    List<User> findAllByOrderByNomeAsc(@Param("quiosqueId") UUID quiosqueId,
        @Param("id") UUID id, @Param("roleName") RoleName roleName);

}
