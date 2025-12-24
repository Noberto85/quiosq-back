package com.webone.quiosq.itg.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OAuthTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private Long expiresIn;
    private String scope;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("public_key")
    private String publicKey;
    @JsonProperty("live_mode")
    private Boolean liveMode;
}
