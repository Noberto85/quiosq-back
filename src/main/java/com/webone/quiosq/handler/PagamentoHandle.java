package com.webone.quiosq.handler;

import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.itg.response.PagamentoApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public abstract class PagamentoHandle {

    private PagamentoHandle next;

    protected PagamentoHandle() {
    }

    public PagamentoApiResponse handleRequest(PedidoRequest base, String acessToken) {

        if (canHandle(base.getPagamento().getMetodo())) {
            return handle(base, acessToken);
        } else if (next != null) {
            next.handleRequest(base, acessToken);
        }
        return null;
    }

    protected abstract boolean canHandle(String type);

    protected abstract PagamentoApiResponse handle(PedidoRequest emailDto, String acessToken);

}
