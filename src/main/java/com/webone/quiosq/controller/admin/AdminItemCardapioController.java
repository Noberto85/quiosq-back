package com.webone.quiosq.controller.admin;

import com.webone.quiosq.controller.response.ItemCardapioResponse;
import com.webone.quiosq.core.queryfilter.ItemCardapioQueryFilter;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.service.ItemCardapioService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/item_cardapio")
@AllArgsConstructor
public class AdminItemCardapioController {

    private final ItemCardapioService service;

    @GetMapping("/{quiosqueId}/pageable")
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

   /*

    @DeleteMapping("/{novoGarcom}/{id}")
    public ResponseEntity<Void> delete(@PathVariable("novoGarcom") Long novoGarcom,
        @PathVariable("id") Long id) {
        service.disable(novoGarcom, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("findAllNotEquals/{quiosqueId}/{id}")
    public ResponseEntity<List<GarcomSelectResponse>> findAllNOtEqualsId(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @PathVariable("id") Long id) {
        return new ResponseEntity<>(
            service.findAllNOtEqualsId(quiosqueId, id),
            HttpStatus.OK);
    }

    @GetMapping("findAllWithStatusTrue/{quiosqueId}")
    public ResponseEntity<List<GarcomSelectResponse>> findAllNOtEqualsId(
        @PathVariable("quiosqueId") UUID quiosqueId) {
        return new ResponseEntity<>(
            service.findAllWithStatusTrue(quiosqueId),
            HttpStatus.OK);
    }


    @PutMapping("/activate")
    public ResponseEntity<Void> activate(
        @RequestBody GarcomRequest request) {
        service.activate(request.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> edit(
        @RequestBody GarcomRequest request) {
        service.edit(request.getId(), request.getNome());
        return new ResponseEntity<>(HttpStatus.OK);
    }*/


}
