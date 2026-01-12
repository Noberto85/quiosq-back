package com.webone.quiosq.controller.admin;

import com.webone.quiosq.controller.request.GarcomRequest;
import com.webone.quiosq.controller.response.GarcomResponse;
import com.webone.quiosq.controller.response.GarcomSelectResponse;
import com.webone.quiosq.core.queryfilter.GarcomQueryFilter;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.service.GarcomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/garcom")
@AllArgsConstructor
public class AdminGarcomController {

    private final GarcomService service;

    @Operation(summary = "Criação de garçom", description = "EndPoint para criação do garçom")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Garçom criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/{quiosqueId}")
    public ResponseEntity<Void> create(@PathVariable("quiosqueId") UUID quiosqueID,
        @RequestBody GarcomRequest createUserDto) {
        service.create(createUserDto, quiosqueID);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{quiosqueId}/pageable")
    public ResponseEntity<PageableDto<GarcomResponse>> findAllPageable(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size,
        @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        GarcomQueryFilter spec
    ) {

        return new ResponseEntity<>(
            service.findAllByPageableSpec(
                spec.toSpecification(quiosqueId), page, size, orderBy,
                direction),
            HttpStatus.OK);

    }

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
    }


}
