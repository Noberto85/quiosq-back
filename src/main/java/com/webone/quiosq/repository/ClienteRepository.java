package com.webone.quiosq.repository;


import com.webone.quiosq.entity.Cliente;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByTelefone(String telefone);

    @Query("SELECT COUNT(c)FROM Cliente c JOIN c.quiosques q WHERE q.id = :quiosqueid AND c.dataAcesso BETWEEN :inicio AND :fim")
    Long getUltimosClientesCadastrados(@Param("quiosqueid") UUID quiosqueid,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim);

}
