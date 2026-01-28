package com.webone.quiosq.service.impl;

import com.webone.quiosq.config.JwtTokenService;
import com.webone.quiosq.controller.request.ValidateToken;
import com.webone.quiosq.controller.response.IdentifcacaoResponse;
import com.webone.quiosq.dto.JwtPayload;
import com.webone.quiosq.dto.LoginClienteDto;
import com.webone.quiosq.dto.LoginUserDto;
import com.webone.quiosq.dto.MesaProjectionDto;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;
import com.webone.quiosq.exception.CodeErro.AuthError;
import com.webone.quiosq.exception.LoginException;
import com.webone.quiosq.itg.SmsService;
import com.webone.quiosq.repository.SystemRoleRepository;
import com.webone.quiosq.service.AuthService;
import com.webone.quiosq.service.MesaService;
import com.webone.quiosq.utils.Utils;
import java.time.Duration;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    public static final String SMS = "sms:";
    private final StringRedisTemplate redisTemplate;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final MesaService mesaService;

    private final SystemRoleRepository systemRoleRepository;

    private final SmsService smsService;

    @Override
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        try {
            // Cria um objeto de autenticação com o email e a senha do usuário
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(),
                    loginUserDto.password());

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
    public RecoveryJwtTokenDto authenticateClient(LoginClienteDto request) {
        try {

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.telefone(), request.password());

            // Autentica o usuário com as credenciais fornecidas
            Authentication authentication = authenticationManager.authenticate(
                usernamePasswordAuthenticationToken);

            ClienteDetailsImpl userDetails = (ClienteDetailsImpl) authentication.getPrincipal();
            return new RecoveryJwtTokenDto(
                jwtTokenService.generateTokenClient(userDetails, request.quiosqueId(),
                    request.mesa(), systemRoleRepository.getTaxa().get()));
        } catch (AuthenticationException e) {
            throw new LoginException(e.getMessage(), AuthError.AUTH_ERROR.getCodeErro());
        }

    }

    @Override
    public IdentifcacaoResponse buildAppIdentificacao(String token) {
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

    @Override
    public void generateCodigoUsuario(String telefone) {
        final String token = Utils.gerarCodigoSms();
        log.info("TOKEN: {}", token);
        smsService.sendToken(token);
        redisTemplate.opsForValue().set(SMS + telefone, token, Duration.ofMinutes(5));

    }

    @Override
    public Boolean validarToken(ValidateToken request) {
        String valor = redisTemplate.opsForValue().get(SMS + request.telefone());
        if(request.token().equals(valor)){
            redisTemplate.opsForValue().getAndDelete(SMS + request.telefone());
            return true;
        }
        return false;
    }

}
