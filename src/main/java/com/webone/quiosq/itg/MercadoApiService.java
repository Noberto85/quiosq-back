package com.webone.quiosq.itg;

import com.webone.quiosq.itg.request.PixRequest;
import com.webone.quiosq.itg.response.OAuthTokenResponse;
import com.webone.quiosq.itg.response.PixResponse;
import com.webone.quiosq.itg.response.UserDTO;

public interface MercadoApiService {

    UserDTO getAuthToken(String accessToken);

    OAuthTokenResponse getAutorizationDetails(String code);

    PixResponse createPix(String acessToken, String idempotencyKey, PixRequest request);
}
