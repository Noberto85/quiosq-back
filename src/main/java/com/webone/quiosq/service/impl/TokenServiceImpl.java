package com.webone.quiosq.service.impl;

import com.webone.quiosq.itg.MercadoApiService;
import com.webone.quiosq.itg.response.UserDTO;
import com.webone.quiosq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final MercadoApiService service;

    @Override
    public void createUserQuisqu(String code) {
        UserDTO authToken = service.getAuthToken(code);
    }
}
