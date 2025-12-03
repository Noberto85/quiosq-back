package com.webone.quiosq.controller;

import com.webone.quiosq.controller.request.ClienteRequest;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;
import com.webone.quiosq.service.ClienteService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cliente")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @ApiResponses(value = {
        @ApiResponse(description = "EndPoint para criação do cliente", responseCode = "201"),
        @ApiResponse(description = "Erro de validação", responseCode = "400"),
        @ApiResponse(description = "Erro interno do servidor", responseCode = "500")
    })
    @PostMapping
    public ResponseEntity<RecoveryJwtTokenDto> createClientAndToken(
        @RequestBody ClienteRequest createUserDto) {
        return new ResponseEntity<>(service.save(createUserDto), HttpStatus.OK);
    }

}
