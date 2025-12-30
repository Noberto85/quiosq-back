package com.webone.quiosq.service.impl;

import com.webone.quiosq.entity.Pagamento;
import com.webone.quiosq.repository.PagamentoRepository;
import com.webone.quiosq.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository repository;

    @Override
    public void create(Pagamento pagamento) {
        repository.save(pagamento);
    }

}
