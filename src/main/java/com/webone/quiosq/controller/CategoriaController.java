package com.webone.quiosq.controller;

import com.webone.quiosq.dto.CategoriaDto;
import com.webone.quiosq.service.CategoriaService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categoria")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping("/{quiosqueId}")
    public ResponseEntity<List<CategoriaDto>> findAll(@PathVariable("quiosqueId") UUID quiosqueId) {
        return new ResponseEntity<>(service.findAll(quiosqueId), HttpStatus.OK);
    }

}
