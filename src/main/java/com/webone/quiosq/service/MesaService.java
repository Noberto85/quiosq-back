package com.webone.quiosq.service;

import com.webone.quiosq.dto.MesaProjectionDto;
import com.webone.quiosq.entity.Garcom;
import com.webone.quiosq.entity.Mesa;
import java.util.List;
import java.util.UUID;

public interface MesaService {

    void salvar(List<Mesa> mesalist);

    MesaProjectionDto buildMesa(UUID quiosqueId, Long mesaId);

    Long findByQuiosqueAndMesa(UUID quiosqueId, Integer numero);

    List<Mesa> findByGarcom(Garcom garcom);

}
