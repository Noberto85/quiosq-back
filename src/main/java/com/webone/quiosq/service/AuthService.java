package com.webone.quiosq.service;


import com.webone.quiosq.controller.request.ValidateToken;
import com.webone.quiosq.controller.response.IdentifcacaoResponse;
import com.webone.quiosq.dto.LoginClienteDto;
import com.webone.quiosq.dto.LoginFuncionarioDto;
import com.webone.quiosq.dto.LoginUserDto;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;

public interface AuthService {

    RecoveryJwtTokenDto authenticate(LoginUserDto loginUserDto);

    RecoveryJwtTokenDto authenticate(LoginClienteDto request);

    RecoveryJwtTokenDto authenticate(LoginFuncionarioDto loginUserDto);

    IdentifcacaoResponse buildAppIdentificacao(String token);

    void generateCodigoUsuario(String telefone);

    Boolean validarToken(ValidateToken request);

}
