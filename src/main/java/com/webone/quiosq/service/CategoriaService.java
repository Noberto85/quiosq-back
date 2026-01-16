package com.webone.quiosq.service;

import com.webone.quiosq.controller.request.CategoriaRequest;
import com.webone.quiosq.dto.CategoriaDto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoriaService {

    @Query("SELECT c FROM Categoria c where c.quiosque =:quiosqueId ORDER BY c.descricao ASC")
    List<CategoriaDto> findAll(@Param("quiosqueId") UUID quiosqueId);

    void create(CategoriaRequest request, UUID quiosqueId);

    void update(CategoriaRequest request);

    void delete(Long id);
}
