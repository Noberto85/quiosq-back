package com.webone.quiosq.repository;


import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.projection.PedidoProjection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(
        value = "SELECT * FROM create_pedido(:quiosqueId, CAST(:mesaId AS BIGINT), :telefone, CAST(:itens AS item_pedido_type[]))",
        nativeQuery = true
    )
    Optional<PedidoProjection> createPedido(
        @Param("quiosqueId") UUID quiosqueId,
        @Param("mesaId") Integer mesaId,
        @Param("telefone") String telefone,
        @Param("itens") String itens
    );


    @Query("SELECT p FROM Pedido p JOIN FETCH p.quiosque qi JOIN FETCH p.mesa m WHERE qi.id = :quiosqueId and m.numero =:numero")
    List<Pedido> findPedido(@Param("quiosqueId") UUID quiosqueId, @Param("numero") Integer numero);
}
