package com.webone.quiosq.controller;

import com.webone.quiosq.controller.response.ItemCardapioResponse;
import com.webone.quiosq.core.queryfilter.ItemCardapioQueryFilter;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.service.ItemCardapioService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/cardapio")
public class ItemCardapioController {

    private final ItemCardapioService service;

    @GetMapping("/{quiosqueId}")
    public ResponseEntity<PageableDto<ItemCardapioResponse>> findAllPageable(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size,
        @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        ItemCardapioQueryFilter spec
    ) {
        PageableDto<ItemCardapioResponse> allByPageableSpec = service.findAllByPageableSpec(
            spec.toSpecification(quiosqueId), page, size, orderBy,
            direction);
        return new ResponseEntity<>(
            allByPageableSpec,
            HttpStatus.OK);

    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
        @RequestPart("file") MultipartFile file) {

        // file -> imagem enviada
        // data -> JSON com os campos do body

        return ResponseEntity.ok("Recebido com sucesso!");
    }


}
