package com.webone.quiosq.controller.admin;

import com.webone.quiosq.controller.request.PedidoUpdateStatusRequest;
import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.core.queryfilter.PedidoQueryFilter;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import com.webone.quiosq.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/pedido")
@AllArgsConstructor
public class AdminPedidoController {

    private final PedidoService service;

    @Operation(
        summary = "Listagem de pedidos",
        description = "Endpoint para listar todos os pedidos cadastrados"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedidos listados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Nenhum pedido encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{quiosqueId}/pageable")
    public ResponseEntity<PageableDto<PedidoResponse>> findAllPageable(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "10") Integer size,
        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
        @RequestParam(value = "direction", defaultValue = "DESC") String direction,
        PedidoQueryFilter spec
    ) {

        return new ResponseEntity<>(
            service.findAllByPageableSpec(
                spec.toSpecification(quiosqueId), page, size, orderBy,
                direction),
            HttpStatus.OK);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> activate(
        @PathVariable("id") Long id,
        @RequestBody PedidoUpdateStatusRequest status) {
        service.updateStatus(id, status.status());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@DeleteMapping("/{novoGarcom}/{id}")
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
    }*/


}
