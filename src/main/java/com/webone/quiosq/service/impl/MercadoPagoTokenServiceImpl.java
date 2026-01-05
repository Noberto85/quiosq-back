package com.webone.quiosq.service.impl;

import com.webone.quiosq.entity.MercadoPagoToken;
import com.webone.quiosq.entity.Quiosque;
import com.webone.quiosq.exception.CodeErro.GeralError;
import com.webone.quiosq.exception.CodeErro.QuiosqueError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.itg.response.OAuthTokenResponse;
import com.webone.quiosq.repository.MercadoPagoTokenRepository;
import com.webone.quiosq.service.MercadoPagoTokenService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MercadoPagoTokenServiceImpl implements MercadoPagoTokenService {

    private final MercadoPagoTokenRepository repository;

    @Override
    @Transactional
    public Boolean isMercadoPago(Long userId) {
        Optional<MercadoPagoToken> byUserId = repository.findByUserId(userId);
        return byUserId.isPresent();
    }

    @Override
    public void create(OAuthTokenResponse oAuthTokenResponse, Quiosque quiosque) {

        MercadoPagoToken build = MercadoPagoToken.builder()
            .accessToken(oAuthTokenResponse.getAccessToken())
            .createdAt(LocalDateTime.now())
            .scope(oAuthTokenResponse.getScope())
            .expiresIn(oAuthTokenResponse.getExpiresIn())
            .liveMode(oAuthTokenResponse.getLiveMode())
            .refreshToken(oAuthTokenResponse.getRefreshToken())
            .publicKey(oAuthTokenResponse.getPublicKey())
            .tokenType(oAuthTokenResponse.getTokenType())
            .userId(oAuthTokenResponse.getUserId())
            .quiosque(quiosque)
            .build();

        repository.save(build);
    }

    @Override
    public String getAccessToken(UUID quiosqueID) {
        return repository.findAccessTokenByQuiosqueId(quiosqueID)
            .orElseThrow(() -> new NotFoundException(
                QuiosqueError.QUIOSQUE_NAO_ENCONTRADO.getCodeErro()));
    }

    @Override
    public MercadoPagoToken findById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new NotFoundException(
            GeralError.NAO_ENCONTRADO.getCodeErro()));
    }

}
