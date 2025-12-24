package com.webone.quiosq.itg.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PixResponse {

    private Long transactionId;        // ID da transação no Mercado Pago
    private String orderId;            // Referência do pedido interno (external_reference)
    private String status;             // Status do pagamento (approved, pending, rejected, etc.)
    private String statusDetail;       // Detalhe do status (ex.: pending_waiting_transfer)
    private BigDecimal amount;         // Valor da transação
    private String currency;           // Moeda (ex.: BRL)
    private String description;        // Descrição da compra
    private String paymentMethod;      // Forma de pagamento (ex.: pix, card)
    private Integer installments;      // Número de parcelas (se aplicável)
    private String dateCreated;        // Data de criação da transação
    private String dateApproved;       // Data de aprovação (se já pago)
    private String ticketUrl;          // Link do comprovante/boleto/PIX
    private String qrCode;             // Código PIX (quando aplicável)
    private String externalReference; // CODIGO EXTERNO PEDIDO


}
