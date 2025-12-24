package com.webone.quiosq.itg.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.webone.quiosq.itg.impl.BaserRequest;
import java.math.BigDecimal;
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

}



