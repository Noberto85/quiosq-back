package com.webone.quiosq.controller;


import com.webone.quiosq.dto.MercadoPagoWebhookDTO;
import com.webone.quiosq.entity.Pagamento;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import com.webone.quiosq.repository.PagamentoRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
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

    private final PagamentoRepository repository;

    @PostMapping(value = "/mercadopago", consumes = "application/json")
    public ResponseEntity<?> receberWebhook(@RequestBody MercadoPagoWebhookDTO payload) {
        log.info("RECEBENDO WEBHOOK: {} ", payload);
        if (Objects.nonNull(payload.getData())) {
            if (payload.getAction().equals("payment.updated")) {
                Optional<Pagamento> byMpPagId = repository.findByMpPagId(
                    Long.parseLong(payload.getData().getId()));
                Pagamento pagamento = byMpPagId.get();
                pagamento.setDataAprovacao(LocalDateTime.now());
                pagamento.setStatus("approved");
                pagamento.getPedido().setStatus(StatusPedidoEnum.EM_PREPARACAO);
                repository.save(pagamento);
            }
        }
        return ResponseEntity.ok().build();
    }
}
