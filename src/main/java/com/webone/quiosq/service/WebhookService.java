package com.webone.quiosq.service;


import com.webone.quiosq.dto.MercadoPagoWebhookDTO;

public interface WebhookService {
    void validatePagamento(MercadoPagoWebhookDTO payload);

}
