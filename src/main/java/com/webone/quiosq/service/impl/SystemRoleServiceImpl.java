package com.webone.quiosq.service.impl;

import com.webone.quiosq.repository.SystemRoleRepository;
import com.webone.quiosq.service.SystemRoleService;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SystemRoleServiceImpl implements SystemRoleService {

    private final SystemRoleRepository repository;

    @Override
    public BigDecimal getTaxa() {
        return repository.getTaxa()
            .orElse(new BigDecimal("0"));
    }
}
