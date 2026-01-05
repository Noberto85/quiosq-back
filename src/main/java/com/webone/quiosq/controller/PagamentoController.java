package com.webone.quiosq.controller;


import com.webone.quiosq.dto.PagamentoResponse;
import com.webone.quiosq.dto.StatusPagamento;
import com.webone.quiosq.service.PagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pagamento")
@AllArgsConstructor
public class PagamentoController {

    private final PagamentoService service;

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponse> getPagamentoApi(
        @PathVariable("id") Long id) {
        return new ResponseEntity<>(service.getPagamentoByPedidoId(id), HttpStatus.OK);
    }

}
