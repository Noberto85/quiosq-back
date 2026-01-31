package com.webone.quiosq.controller;


import com.webone.quiosq.dto.RoleDto;
import com.webone.quiosq.entity.enums.RoleName;
import com.webone.quiosq.service.RoleService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
@AllArgsConstructor
public class RoleController {

    private RoleService service;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getPagamentoApi() {
        return new ResponseEntity<>(
            service.listAllNotgEquals(List.of(RoleName.ROLE_SYSTEM_ADMIN, RoleName.ROLE_CLIENTE)),
            HttpStatus.OK);
    }
}
