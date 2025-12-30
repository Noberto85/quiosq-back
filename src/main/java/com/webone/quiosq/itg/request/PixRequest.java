package com.webone.quiosq.itg.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.webone.quiosq.itg.impl.BaserRequest;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PixRequest implements BaserRequest {

    private BigDecimal transactionAmount;
    private String description;
    private String paymentMethodId;
    private String externalReference;
    private Payer payer;
    private String notificationUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime dateOfExpiration;

}



