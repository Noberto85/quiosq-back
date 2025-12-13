package com.webone.quiosq.dto;

import lombok.Data;

@Data
public class PaymentDTO {

    private String method;
    private CardDTO card;

}
