package com.webone.quiosq.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/produto")
public class ProdutoController {


    @GetMapping
    public ResponseEntity<Void> refreshToken() {

        // String token = JWTUtil.generateToken(usuaioAutenticado.getEmail());
        // response.setHeader("Authorization", "Bearer " + usuaioAutenticado.getEmail());
        System.out.println("");
        return ResponseEntity.noContent().build();
    }


}
