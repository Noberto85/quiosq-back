package com.webone.quiosq.repository;


import com.webone.quiosq.entity.Categoria;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c WHERE c.quiosque.id = :quiosqueId ORDER BY c.descricao ASC")
    List<Categoria> findAllByQuiosqueIdOrderByDescricaoAsc(@Param("quiosqueId") UUID quiosqueId);

}
