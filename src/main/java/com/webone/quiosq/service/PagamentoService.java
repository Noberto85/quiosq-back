package com.webone.quiosq.service;

import com.webone.quiosq.dto.PagamentoResponse;
import com.webone.quiosq.dto.StatusPagamento;
import com.webone.quiosq.entity.Pagamento;

public interface PagamentoService {

    Pagamento create(Pagamento pagamento);

    StatusPagamento getStatusById(Long id);

    PagamentoResponse getPagamentoByPedidoId(Long id);


}
