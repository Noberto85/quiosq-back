package com.webone.quiosq.controller.funcionario;


import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.core.queryfilter.FuncQueryFilter;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import com.webone.quiosq.service.FuncionarioService;
import java.util.List;
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
@RequestMapping("/api/v1/func/garcom")
@AllArgsConstructor
public class FuncGarcomController {

    private final FuncionarioService service;

    @GetMapping("findAllByStatus/{quiosqueId}")
    public ResponseEntity<List<PedidoResponse>> findAllNOtEqualsId(
        @PathVariable("quiosqueId") UUID quiosqueId,
        @RequestParam(value = "status", required = false) StatusPedidoEnum status,
        @RequestParam(value = "mesa", required = false) String numeroMesa,
        FuncQueryFilter spec) {
        return new ResponseEntity<>(
            service.findAllByStatus(spec.toSpecification(quiosqueId, status, numeroMesa)),
            HttpStatus.OK);
    }
}
