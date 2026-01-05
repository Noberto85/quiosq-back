package com.webone.quiosq.itg;

import com.webone.quiosq.itg.request.PixRequest;
import com.webone.quiosq.itg.response.OAuthTokenResponse;
import com.webone.quiosq.itg.response.PagamentoApiResponse;
import com.webone.quiosq.itg.response.UserDTO;

public interface MercadoApiService {

    UserDTO getAuthToken(String accessToken);

    OAuthTokenResponse getAutorizationDetails(String code);

    PagamentoApiResponse createPix(String acessToken, String idempotencyKey, PixRequest request);

    PagamentoApiResponse getApiPagamento(String accessToken, Long id);

}
