package com.webone.quiosq.service.impl;

import com.webone.quiosq.config.JwtTokenService;
import com.webone.quiosq.controller.response.IdentifcacaoResponse;
import com.webone.quiosq.dto.ClientDetails;
import com.webone.quiosq.dto.JwtPayload;
import com.webone.quiosq.dto.LoginUserDto;
import com.webone.quiosq.dto.MesaProjectionDto;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;
import com.webone.quiosq.exception.CodeErro;
import com.webone.quiosq.exception.CodeErro.AuthError;
import com.webone.quiosq.exception.CodeErro.RoleError;
import com.webone.quiosq.exception.LoginException;
import com.webone.quiosq.service.AuthService;
import com.webone.quiosq.service.MesaService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final MesaService mesaService;

    @Override
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
       try {
           // Cria um objeto de autenticação com o email e a senha do usuário
           UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
               new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

           // Autentica o usuário com as credenciais fornecidas
           Authentication authentication = authenticationManager.authenticate(
               usernamePasswordAuthenticationToken);

           // Obtém o objeto UserDetails do usuário autenticado
           UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

           // Gera um token JWT para o usuário autenticado
           return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
       } catch (AuthenticationException e) {
           throw new LoginException(e.getMessage(), AuthError.AUTH_ERROR.getCodeErro());
       }
    }

    @Override
    public RecoveryJwtTokenDto authenticateClient(ClientDetails clientDetails) {
        return new RecoveryJwtTokenDto(jwtTokenService.generateTokenClient(clientDetails));
    }

    @Override
    public IdentifcacaoResponse buildIdentificacao(String token) {
        IdentifcacaoResponse response = new IdentifcacaoResponse();
        JwtPayload parse = jwtTokenService.parse(token);
        MesaProjectionDto dto = mesaService.buildMesa(parse.getQuiosqueId(),
            parse.getMesaId());
        response.setMesa(dto.getMesa());
        response.setQuiosque(dto.getQuiosque());
        response.setGarcom(dto.getGarcom());
        response.setQuiosqueId(dto.getQuiosqueId());
        return response;
    }
}
