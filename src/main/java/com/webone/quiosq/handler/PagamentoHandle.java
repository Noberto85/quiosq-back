package com.webone.quiosq.handler;

import com.webone.quiosq.controller.request.PedidoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public abstract class PagamentoHandle {

    private PagamentoHandle next;

    protected PagamentoHandle() {
    }

    public void handleRequest(PedidoRequest base, String acessToken) {
        if (canHandle(base.getPagamento().getMetodo())) {
            handle(base, acessToken);
        } else if (next != null) {
            next.handleRequest(base, acessToken);
        }
    }

    protected abstract boolean canHandle(String type);

    protected abstract void handle(PedidoRequest emailDto, String acessToken);
}
