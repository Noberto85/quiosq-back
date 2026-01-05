package com.webone.quiosq.controller;

import com.webone.quiosq.controller.request.ClienteRequest;
import com.webone.quiosq.dto.CategoriaDto;
import com.webone.quiosq.service.CategoriaService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categoria")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

}
