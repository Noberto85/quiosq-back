package com.webone.quiosq.repository;


import com.webone.quiosq.entity.Pagamento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    Optional<Pagamento> findByMpPagId(Long mpPagId);

    @Query("SELECT p.status FROM Pagamento p WHERE p.id =:id")
    Optional<String> getStatusById(@Param("id") Long id);

}
