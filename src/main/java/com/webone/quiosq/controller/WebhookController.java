package com.webone.quiosq.controller;


import com.webone.quiosq.dto.MercadoPagoWebhookDTO;
import com.webone.quiosq.service.WebhookService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("${notification.base}")
@RestController
@AllArgsConstructor
@RequestMapping("/api/webhook")
@Log4j2
public class WebhookController {


    private final WebhookService service;

    @PostMapping(value = "/mercadopago", consumes = "application/json")
    public ResponseEntity<?> receberWebhook(@RequestBody MercadoPagoWebhookDTO payload) {
        service.validatePagamento(payload);
        return ResponseEntity.ok().build();
    }
}
