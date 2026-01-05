package com.webone.quiosq.itg.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PagamentoApiResponse {

    private Long id;
    private String notificationUrl;
    private String status;
    private String statusDetail;
    private TransacaoDetais transactionDetails;
    private PointOfInteraction pointOfInteraction;
    private OffsetDateTime dateCreated;
    private OffsetDateTime dateLastUpdated;
    private OffsetDateTime dateOfExpiration;
    private BigDecimal transactionAmount;
    private String externalReference;
    private String paymentMethodId;
    private String paymentTypeId;
    private String currencyId;

}



