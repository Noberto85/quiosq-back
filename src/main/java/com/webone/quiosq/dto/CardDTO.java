package com.webone.quiosq.dto;

import lombok.Data;

@Data
public class CardDTO {
    private String number;
    private String name;
    private String expiry;
    private String cvv;

}
