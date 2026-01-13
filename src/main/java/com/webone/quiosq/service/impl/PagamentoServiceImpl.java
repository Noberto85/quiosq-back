package com.webone.quiosq.service.impl;

import com.webone.quiosq.dto.PagamentoResponse;
import com.webone.quiosq.dto.StatusPagamento;
import com.webone.quiosq.entity.Pagamento;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import com.webone.quiosq.exception.CodeErro.PagamentoError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.itg.MercadoApiService;
import com.webone.quiosq.itg.response.PagamentoApiResponse;
import com.webone.quiosq.repository.PagamentoRepository;
import com.webone.quiosq.service.MercadoPagoTokenService;
import com.webone.quiosq.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PagamentoServiceImpl implements PagamentoService {

    private static final String PENDING = "pending";
    private final PagamentoRepository repository;
    private final MercadoPagoTokenService mercadoPagoTokenService;
    private final MercadoApiService mercadoApiService;

    @Override
    public Pagamento create(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    @Override
    public StatusPagamento getStatusById(Long id) {
        return repository.getStatusById(id).map(StatusPagamento::new)
            .orElseThrow(() -> new NotFoundException(PagamentoError.PAGAMENTO_ERROR.getCodeErro()));
    }

    @Override
    public PagamentoResponse getPagamentoByPedidoId(Long id) {
        final Pagamento pagamento = repository.getPagamentoByPedidoId(id)
            .orElseThrow(() -> new NotFoundException(
                PagamentoError.PAGAMENTO_ERROR.getCodeErro()));
        final String accessToken = mercadoPagoTokenService.getAccessToken(
            pagamento.getPedido().getQuiosque().getId());
        final PagamentoApiResponse apiPagamento = mercadoApiService.getApiPagamento(accessToken,
            pagamento.getMpPagId());

        if (!apiPagamento.getStatus().equals(PENDING)) {
            pagamento.setStatus(apiPagamento.getStatus());
            pagamento.setStatusDetail(apiPagamento.getStatusDetail());
            pagamento.getPedido().setStatus(getStatus(apiPagamento.getStatus()));
            repository.save(pagamento);

        }
        return new PagamentoResponse(apiPagamento, pagamento.getId());
    }

    private StatusPedidoEnum getStatus(String status) {
        if (status.equals("approved")) {
            return StatusPedidoEnum.AGUARDANDO_PREPARO;
        } else {
            return StatusPedidoEnum.CANCELADO;
        }
    }
}
