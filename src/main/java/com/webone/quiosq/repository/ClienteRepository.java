package com.webone.quiosq.repository;


import com.webone.quiosq.entity.Cliente;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c JOIN FETCH c.quiosque q " +
        "WHERE c.telefone = :telefone AND q.id = :quiosqueId")
    Optional<Cliente> findByTelefoneAndQuiosqueId(@Param("telefone") String telefone,
        @Param("quiosqueId") UUID quiosqueId);

}
