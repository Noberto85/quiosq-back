package com.webone.quiosq.dto;

import com.webone.quiosq.itg.response.PagamentoApiResponse;
import com.webone.quiosq.utils.DateUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PagamentoResponse {

    private Long id;
    private String status;
    private String qrCode;
    private BigDecimal valor;
    private String transactionId;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastUpdated;
    private LocalDateTime dateOfExpiration;

    public PagamentoResponse(PagamentoApiResponse res, Long pagamentoId) {
        this.id = pagamentoId;
        this.status = res.getStatus();
        this.qrCode = res.getPointOfInteraction().getTransactionData().getQrCode();
        this.valor = res.getTransactionAmount();
        this.transactionId = res.getTransactionDetails().getTransactionId();
        this.dateCreated = DateUtils.convert(res.getDateCreated());
        this.dateLastUpdated = DateUtils.convert(res.getDateLastUpdated());
        this.dateOfExpiration = DateUtils.convert(res.getDateOfExpiration());

    }
}



