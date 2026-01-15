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

    @Query("SELECT c FROM Categoria c join c.quiosque q WHERE q.id =: id")
    List<Categoria> findAllByOrderByDescricaoAsc(@Param("id") UUID quiosqueId);

}
