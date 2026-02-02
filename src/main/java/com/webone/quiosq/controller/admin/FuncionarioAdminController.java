package com.webone.quiosq.controller.admin;

import com.webone.quiosq.controller.request.FuncionarioCreateRequest;
import com.webone.quiosq.controller.request.GarcomRequest;
import com.webone.quiosq.controller.response.GarcomResponse;
import com.webone.quiosq.controller.response.GarcomSelectResponse;
import com.webone.quiosq.core.queryfilter.FuncionarioQueryFilter;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.service.FuncionarioService;
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
@RequestMapping("/api/v1/admin/funcionario")
@AllArgsConstructor
public class FuncionarioAdminController {

    private final FuncionarioService service;

    @ApiResponses(value = {
        @ApiResponse(description = "EndPoint para criação de Funcionario", responseCode = "201"),
        @ApiResponse(description = "Erro de validação", responseCode = "400"),
        @ApiResponse(description = "Erro interno do servidor", responseCode = "500")
    })
    @PostMapping("{quiosqueId}")
    public ResponseEntity<Void> create(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @RequestBody FuncionarioCreateRequest request) {
        service.create(request, quiosqueId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("findAllWithStatusTrue/{quiosqueId}")
    public ResponseEntity<List<GarcomSelectResponse>> findAllNOtEqualsId(
        @PathVariable("quiosqueId") UUID quiosqueId) {
        return new ResponseEntity<>(
            service.findAllWithStatusTrue(quiosqueId),
            HttpStatus.OK);
    }

    @GetMapping("/{quiosqueId}/pageable")
    public ResponseEntity<PageableDto<GarcomResponse>> findAllPageable(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size,
        @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction,
        FuncionarioQueryFilter spec
    ) {

        return new ResponseEntity<>(
            service.findAllByPageableSpec(
                spec.toSpecification(quiosqueId), page, size, orderBy,
                direction),
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

    @GetMapping("findAllNotEquals/{quiosqueId}/{id}")
    public ResponseEntity<List<GarcomSelectResponse>> findAllNOtEqualsId(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @PathVariable("id") UUID id) {
        return new ResponseEntity<>(
            service.findAllNOtEqualsId(quiosqueId, id),
            HttpStatus.OK);
    }

    @DeleteMapping("/{novoGarcom}/{id}")
    public ResponseEntity<Void> delete(@PathVariable("novoGarcom") UUID novoGarcom,
        @PathVariable("id") UUID id) {
        service.disable(novoGarcom, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
