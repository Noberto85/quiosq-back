package com.webone.quiosq.repository;


import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import com.webone.quiosq.projection.PedidoProjection;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>,
    JpaSpecificationExecutor<Pedido> {

    @Query(
        value = "SELECT * FROM func_create_pedido(:nomePedido,:quiosqueId, CAST(:mesaId AS BIGINT), :telefone, CAST(:itens AS item_pedido_type[]))",
        nativeQuery = true
    )
    Optional<PedidoProjection> createPedido(
        @Param("nomePedido") String nomePedido,
        @Param("quiosqueId") UUID quiosqueId,
        @Param("mesaId") Integer mesaId,
        @Param("telefone") String telefone,
        @Param("itens") String itens
    );

    @Query("SELECT p FROM Pedido p "
        + "JOIN FETCH p.quiosque qi "
        + "JOIN FETCH p.mesa m WHERE qi.id = :quiosqueId AND m.numero =:numero")
    List<Pedido> findPedido(@Param("quiosqueId") UUID quiosqueId, @Param("numero") Integer numero);

    @Query("SELECT p FROM Pedido p "
        + "JOIN FETCH p.itens it "
        + "JOIN FETCH it.itemCardapio "
        + "JOIN FETCH p.quiosque qi "
        + "JOIN FETCH p.cliente cli where cli.telefone =:telefone AND qi.id =:quiosqueId AND p.dataInit BETWEEN :dataInit AND :dataFim ORDER BY p.id DESC")
    List<Pedido> findByClienteId(@Param("quiosqueId") UUID quiosqueId,
        @Param("telefone") String telefone, @Param("dataInit") LocalDateTime dataInit, @Param("dataFim") LocalDateTime dataFim);

    @EntityGraph(attributePaths = {"itens", "itens.itemCardapio"})
    Page<Pedido> findAll(Specification<Pedido> spec, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.dataContagem BETWEEN :inicio AND :fim AND p.quiosque.id =:quiosqueId AND p.status <> 'AGUARDANDO_PAGAMENTO'")
    Long getUltimosPedidos(@Param("quiosqueId") UUID quiosqueId, @Param("inicio") LocalDateTime inicio,  @Param("fim") LocalDateTime fim);

    @Query("SELECT SUM(i.valorSoma) " +
        "FROM Pedido p JOIN p.itens i " +
        "WHERE p.dataContagem BETWEEN :start AND :end " +
        "AND p.status = :status AND p.quiosque.id =:quiosqueId")
    Optional<BigDecimal> calcularReceitaDoDia(
        @Param("quiosqueId") UUID quiosqueId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end,
        @Param("status") StatusPedidoEnum status
    );


}
