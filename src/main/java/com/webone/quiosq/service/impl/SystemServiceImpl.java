package com.webone.quiosq.service.impl;

import com.webone.quiosq.dto.EstastisticasDto;
import com.webone.quiosq.repository.QuiosqueRepository;
import com.webone.quiosq.service.SystemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SystemServiceImpl implements SystemService {

    private  final QuiosqueRepository quiosqueRepository;
    @Override
    public EstastisticasDto loadDash() {
        final Integer qtdQuiosque = quiosqueRepository.getAllCountQuiosque().orElse(0);

        return EstastisticasDto.builder()
            .qtdTotQuiosque(qtdQuiosque)
            .build();
    }
}
