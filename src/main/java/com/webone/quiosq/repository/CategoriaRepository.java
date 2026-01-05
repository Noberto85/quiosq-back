package com.webone.quiosq.repository;


import com.webone.quiosq.entity.Categoria;
import com.webone.quiosq.entity.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findAllByOrderByDescricaoAsc();


}
