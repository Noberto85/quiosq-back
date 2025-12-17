package com.webone.quiosq.service.impl;

import com.webone.quiosq.dto.MesaProjectionDto;
import com.webone.quiosq.exception.CodeErro.QuiosqueError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.projection.MesaInfoProjection;
import com.webone.quiosq.repository.MesaRepository;
import com.webone.quiosq.service.MesaService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MesaServiceImpl implements MesaService {

    private MesaRepository mesaRepository;

    @Override
    public MesaProjectionDto buildMesa(UUID quiosqueId, Long mesaId) {

        MesaInfoProjection mesaInfo = mesaRepository.getMesaInfo(quiosqueId, mesaId)
            .orElseThrow(() -> new NotFoundException(
                QuiosqueError.MESA_NAO_ENCONTRADO.getCodeErro()));
        return new MesaProjectionDto(mesaInfo);

    }

    @Override
    public Long findByQuiosqueAndMesa(UUID quiosqueId, Integer numero) {
       return mesaRepository.getID(quiosqueId, numero).orElseThrow(() -> new NotFoundException(
           QuiosqueError.MESA_NAO_ENCONTRADO.getCodeErro()));

    }
}
