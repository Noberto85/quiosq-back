package com.webone.quiosq.service;


import com.webone.quiosq.dto.MercadoPagoWebhookDTO;
import java.util.Map;

public interface WebhookService {

    void validatePagamento(MercadoPagoWebhookDTO payload, Map<String, String> headers);

    void validatePagamentoHml(Long payload);

}
