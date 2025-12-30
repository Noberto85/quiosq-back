package com.webone.quiosq.service.impl;

import com.webone.quiosq.dto.StatusPagamento;
import com.webone.quiosq.entity.Pagamento;
import com.webone.quiosq.exception.CodeErro.PagamentoError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.repository.PagamentoRepository;
import com.webone.quiosq.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private final PagamentoRepository repository;

    @Override
    public Pagamento create(Pagamento pagamento) {
       return repository.save(pagamento);
    }

    @Override
    public StatusPagamento getStatusById(Long id) {
        return repository.getStatusById(id).map(StatusPagamento::new)
            .orElseThrow(() -> new NotFoundException(PagamentoError.PAGAMENTO_ERROR.getCodeErro()));
    }

}
