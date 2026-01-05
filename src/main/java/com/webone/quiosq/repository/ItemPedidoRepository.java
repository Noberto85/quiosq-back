package com.webone.quiosq.repository;

import com.webone.quiosq.entity.ItemCardapio;
import com.webone.quiosq.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>,
    JpaSpecificationExecutor<ItemCardapio> {

}
