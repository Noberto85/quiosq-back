package com.webone.quiosq.service;

import com.webone.quiosq.dto.MesaProjectionDto;
import java.util.UUID;

public interface MesaService {

    MesaProjectionDto buildMesa(UUID quiosqueId, Long mesaId);

}
