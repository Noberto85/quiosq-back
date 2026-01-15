package com.webone.quiosq.service;

import com.webone.quiosq.dto.CategoriaDto;
import java.util.List;
import java.util.UUID;

public interface CategoriaService {
    List<CategoriaDto> findAll(UUID quiosqueId);
}
