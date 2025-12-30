package com.webone.quiosq.controller;


import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.itg.response.PagamentoApiResponse;
import com.webone.quiosq.service.PedidoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pedido")
@AllArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @ApiResponses(value = {
        @ApiResponse(description = "EndPoint para criação do cliente", responseCode = "201"),
        @ApiResponse(description = "Erro de validação", responseCode = "400"),
        @ApiResponse(description = "Erro interno do servidor", responseCode = "500")
    })
    @PostMapping
    public ResponseEntity<PagamentoApiResponse> createPedido(
        @RequestBody PedidoRequest createUserDto) {
        return new ResponseEntity<>(service.createPedido(createUserDto), HttpStatus.OK);
    }

    @GetMapping("/{mesa}/{quisoqueId}")
    public ResponseEntity<List<PedidoResponse>> findAllPedidos(
        @PathVariable("mesa") Integer mesa,
        @PathVariable("quisoqueId") UUID quisque) {
        return new ResponseEntity<>(service.findPedido(quisque, mesa), HttpStatus.OK);
    }
}
