package com.webone.quiosq.itg;

import com.webone.quiosq.itg.response.UserDTO;

public interface MercadoApiService {
    UserDTO getAuthToken(String code);
}
