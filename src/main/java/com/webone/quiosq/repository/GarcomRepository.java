package com.webone.quiosq.repository;

import com.webone.quiosq.entity.Garcom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GarcomRepository extends JpaRepository<Garcom, Long>,
    JpaSpecificationExecutor<Garcom> {

    Optional<Garcom> findByCpfAndQuiosqueId(String cpf, UUID quiodqueId);

    @Query("SELECT g FROM Garcom g "
        + "JOIN g.quiosque q " +
        "WHERE g.id <> :id AND g.ativo = true AND q.id =:quiosqueId " +
        "ORDER BY g.nome ASC")
    List<Garcom> findAllByOrderByNomeAsc(@Param("quiosqueId") UUID quiosqueId,@Param("id") Long id);


}
