package com.webone.quiosq.controller.admin;

import com.webone.quiosq.controller.request.MesaRequest;
import com.webone.quiosq.controller.response.MesaResponse;
import com.webone.quiosq.core.queryfilter.MesaQueryFilter;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.service.MesaService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/mesa")
@AllArgsConstructor
public class AdminMesaController {

    private final MesaService service;


    @GetMapping("/{quiosqueId}/pageable")
    public ResponseEntity<PageableDto<MesaResponse>> findAllPageable(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size,
        @RequestParam(value = "orderBy", defaultValue = "numero") String orderBy,
        @RequestParam(value = "direction", defaultValue = "DESC") String direction,
        MesaQueryFilter spec
    ) {

        return new ResponseEntity<>(
            service.findAllByPageableSpec(
                spec.toSpecification(quiosqueId), page, size, orderBy,
                direction),
            HttpStatus.OK);

    }

    @PostMapping("/{quiosqueId}")
    public ResponseEntity<Void> create(@PathVariable("quiosqueId") UUID quiosqueId,
        @RequestBody MesaRequest request) {
        service.create(request, quiosqueId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/disable")
    public ResponseEntity<Void> disable(@PathVariable("id") Long id) {
        service.disable(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody MesaRequest request) {
        service.update(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
