package com.webone.quiosq.repository;

import com.webone.quiosq.entity.ClienteQuiosque;
import com.webone.quiosq.entity.ClienteQuiosqueId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteQuiosqueRepository extends
    JpaRepository<ClienteQuiosque, ClienteQuiosqueId> {

}
