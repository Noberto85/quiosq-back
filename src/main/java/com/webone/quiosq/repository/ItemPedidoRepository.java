package com.webone.quiosq.repository;

import com.webone.quiosq.entity.ItemCardapio;
import com.webone.quiosq.entity.ItemPedido;
import com.webone.quiosq.projection.TotalVendidoProjection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>,
    JpaSpecificationExecutor<ItemCardapio> {

    @Query("SELECT ic.descricao AS descricao, "
        + "SUM(ip.quantidade) AS totalVendido " +
        "FROM ItemPedido ip " +
        "JOIN ip.itemCardapio ic " +
        "JOIN ip.pedido pe " +
        "WHERE pe.quiosque.id = :quiosqueId " +
        "GROUP BY ic.descricao, ic.id " +
        "ORDER BY totalVendido DESC LIMIT 10")
    List<TotalVendidoProjection> findMaisVendidos(@Param("quiosqueId") UUID quiosqueId);

}
