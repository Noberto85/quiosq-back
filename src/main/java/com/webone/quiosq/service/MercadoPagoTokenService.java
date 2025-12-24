package com.webone.quiosq.service;

import com.webone.quiosq.entity.Quiosque;
import com.webone.quiosq.itg.response.OAuthTokenResponse;
import java.util.UUID;

public interface MercadoPagoTokenService {

    Boolean isMercadoPago(Long userId);

    void create(OAuthTokenResponse oAuthTokenResponse, Quiosque quiosque);

    String getAccessToken(UUID quiosqueID);
}
