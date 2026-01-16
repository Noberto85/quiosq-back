package com.webone.quiosq.repository;

import com.webone.quiosq.entity.ItemCardapio;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Long>,
    JpaSpecificationExecutor<ItemCardapio> {

    @Query("SELECT i FROM ItemCardapio i JOIN FETCH i.categoria c JOIN FETCH c.quiosque qi "
        + "WHERE qi.id =:quiosqueId AND (:descricao is null or c.descricao =:descricao) AND i.ativo is true ORDER BY i.nome")
    List<ItemCardapio> findAllWithCategoriaAndQuiosque(@Param("quiosqueId") UUID quiosqueId,
        @Param("descricao") String descricao);


}
