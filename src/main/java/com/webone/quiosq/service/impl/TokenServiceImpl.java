package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.request.QuiosqueRequest;
import com.webone.quiosq.dto.CreateUserDto;
import com.webone.quiosq.entity.enums.RoleName;
import com.webone.quiosq.itg.MercadoApiService;
import com.webone.quiosq.itg.response.UserDTO;
import com.webone.quiosq.service.MercadoPagoTokenService;
import com.webone.quiosq.service.QuiosqueService;
import com.webone.quiosq.service.TokenService;
import com.webone.quiosq.service.UserService;
import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

@Service
@AllArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final MercadoApiService service;
    private final MercadoPagoTokenService mercadoPagoTokenService;
    private final QuiosqueService quiosqueService;


    @Override
    @Transactional
    public RedirectView createUserQuisqu(String code) {
        var autorizationDetails = service.getAutorizationDetails(code);
        Boolean isPresent = mercadoPagoTokenService.isMercadoPago(autorizationDetails.getUserId());
        if (isPresent) {
            return new RedirectView("/success.html");
        }
        UserDTO userDTO = service.getAuthToken(autorizationDetails.getAccessToken());
        QuiosqueRequest quiosqueRequest = new QuiosqueRequest();
        quiosqueRequest.setCnpj(userDTO.getIdentification().getNumber());
        quiosqueRequest.setNome(String.format("%s %s", userDTO.getFirstName(),
            Objects.nonNull(userDTO.getLastName()) ? userDTO.getLastName() : ""));
        quiosqueRequest.setEmail(userDTO.getEmail());
        quiosqueRequest.setTelefone(String.format("%s%s", userDTO.getPhone().getArea_code(),
            userDTO.getPhone().getNumber()));
        var quiosque = quiosqueService.save(quiosqueRequest);
        mercadoPagoTokenService.create(autorizationDetails, quiosque);

        return new RedirectView("/success.html");
    }
}
