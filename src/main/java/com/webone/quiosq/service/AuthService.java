package com.webone.quiosq.service;


import com.webone.quiosq.controller.request.ValidateToken;
import com.webone.quiosq.controller.response.IdentifcacaoResponse;
import com.webone.quiosq.dto.LoginClienteDto;
import com.webone.quiosq.dto.LoginUserDto;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;

public interface AuthService {

    RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto);

    RecoveryJwtTokenDto authenticateClient(LoginClienteDto request);

    IdentifcacaoResponse buildAppIdentificacao(String token);

    void generateCodigoUsuario(String telefone);

    Boolean validarToken(ValidateToken request);

}
