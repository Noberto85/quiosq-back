package com.webone.quiosq.controller.admin;

import com.webone.quiosq.controller.request.CategoriaRequest;
import com.webone.quiosq.dto.CategoriaDto;
import com.webone.quiosq.service.CategoriaService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/categoria")
@AllArgsConstructor
public class AdminCategoriaController {

    private final CategoriaService service;

    @GetMapping("/{quiosqueId}")
    public ResponseEntity<List<CategoriaDto>> findAll(
        @PathVariable("quiosqueId") UUID quiosqueId
    ) {
        return new ResponseEntity<>(
            service.findAll(quiosqueId),
            HttpStatus.OK);

    }

    @PostMapping("/{quiosqueId}")
    public ResponseEntity<Void> create(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @Valid @RequestBody CategoriaRequest request
    ) {
        service.create(request, quiosqueId);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<Void> update(
        @Valid @RequestBody CategoriaRequest request
    ) {
        service.update(request);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> update(
        @PathVariable("id") Long id
    ) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}
