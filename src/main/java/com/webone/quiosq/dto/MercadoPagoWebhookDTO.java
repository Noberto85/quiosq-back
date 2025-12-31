package com.webone.quiosq.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MercadoPagoWebhookDTO {

    private String action;

    @JsonProperty("api_version")
    private String apiVersion;

    private DataPayload data;

    @JsonProperty("date_created")
    private String dateCreated;

    private String id;

    @JsonProperty("live_mode")
    private boolean liveMode;

    private String type;

    @JsonProperty("user_id")
    private Long userId;

    @Data
    public static class DataPayload {
        private String id;
    }
}
