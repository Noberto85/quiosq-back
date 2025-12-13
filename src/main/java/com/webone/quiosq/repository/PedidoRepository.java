package com.webone.quiosq.repository;


import com.webone.quiosq.entity.Pedido;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Modifying
    @Transactional()
    @Query(
        value = "CALL create_pedido(:quiosqueId, :mesaId, :clienteId, CAST(:itens AS item_pedido_type[]))",
        nativeQuery = true
    )
    void createPedido(
        @Param("quiosqueId") UUID quiosqueId,
        @Param("mesaId") Integer mesaId,
        @Param("clienteId") Integer clienteId,
        @Param("itens") String itens
    );
}
