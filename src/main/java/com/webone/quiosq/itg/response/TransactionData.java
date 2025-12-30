package com.webone.quiosq.itg.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionData {

    private String qrCode;
    private byte[] qrCodeBase64;
    private String transactionId;
}
