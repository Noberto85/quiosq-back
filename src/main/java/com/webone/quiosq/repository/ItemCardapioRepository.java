package com.webone.quiosq.repository;

import com.webone.quiosq.entity.ItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Long>,
    JpaSpecificationExecutor<ItemCardapio> {


}
