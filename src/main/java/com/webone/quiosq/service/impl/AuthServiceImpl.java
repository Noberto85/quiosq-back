package com.webone.quiosq.service.impl;

import com.webone.quiosq.config.JwtTokenService;
import com.webone.quiosq.controller.request.ValidateToken;
import com.webone.quiosq.controller.response.IdentifcacaoResponse;
import com.webone.quiosq.dto.JwtPayload;
import com.webone.quiosq.dto.LoginClienteDto;
import com.webone.quiosq.dto.LoginFuncionarioDto;
import com.webone.quiosq.dto.LoginUserDto;
import com.webone.quiosq.dto.MesaProjectionDto;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;
import com.webone.quiosq.entity.Cliente;
import com.webone.quiosq.exception.ClienteException;
import com.webone.quiosq.exception.CodeErro.AuthError;
import com.webone.quiosq.exception.CodeErro.ClienteError;
import com.webone.quiosq.exception.LoginException;
import com.webone.quiosq.itg.SmsService;
import com.webone.quiosq.repository.ClienteRepository;
import com.webone.quiosq.repository.SystemRoleRepository;
import com.webone.quiosq.service.AuthService;
import com.webone.quiosq.service.MesaService;
import com.webone.quiosq.utils.Utils;
import java.time.Duration;
import java.util.Optional;
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

    private final ClienteRepository repository;

    @Override
    public RecoveryJwtTokenDto authenticate(LoginUserDto loginUserDto) {
        try {
            // Cria um objeto de autenticação com o email e a senha do usuário
            Authentication authentication = getAuthentication(loginUserDto.email(),loginUserDto.password());

            // Obtém o objeto UserDetails do usuário autenticado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Gera um token JWT para o usuário autenticado
            return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
        } catch (AuthenticationException e) {
            throw new LoginException(e.getMessage(), AuthError.AUTH_ERROR.getCodeErro());
        }
    }

    private Authentication getAuthentication(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(username,
                password);
        return authenticationManager.authenticate(
            usernamePasswordAuthenticationToken);

    }

    @Override
    public RecoveryJwtTokenDto authenticate(LoginClienteDto request) {
        try {

            Authentication authentication = getAuthentication(request.telefone(),request.password());

            ClienteDetailsImpl userDetails = (ClienteDetailsImpl) authentication.getPrincipal();
            return new RecoveryJwtTokenDto(
                jwtTokenService.generateTokenClient(userDetails, request.quiosqueId(),
                    request.mesa(), systemRoleRepository.getTaxa().get()));
        } catch (AuthenticationException e) {
            throw new LoginException(e.getMessage(), AuthError.AUTH_ERROR.getCodeErro());
        }

    }

    @Override
    public RecoveryJwtTokenDto authenticate(LoginFuncionarioDto loginUserDto) {
        try {
            // Cria um objeto de autenticação com o email e a senha do usuário
            Authentication authentication = getAuthentication(loginUserDto.login(),loginUserDto.password());

            // Obtém o objeto UserDetails do usuário autenticado
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Gera um token JWT para o usuário autenticado
            return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
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
        final Optional<Cliente> clienteOpt = repository.findByTelefone(telefone);
        if (clienteOpt.isPresent()) {
            throw new ClienteException(ClienteError.CLIENTE_JA_CADASTRADO.getCodeErro());
        }

        // Gera token e envia
        final String token = Utils.gerarCodigoSms();
        log.info("TOKEN: {}", token);
        smsService.sendToken(token);

        // Salva token com expiração de 5 minutos
        redisTemplate.opsForValue().set(SMS + telefone, token, Duration.ofMinutes(5));
    }


    @Override
    public Boolean validarToken(ValidateToken request) {
        String valor = redisTemplate.opsForValue().get(SMS + request.telefone());
        if (request.token().equals(valor)) {
            redisTemplate.opsForValue().getAndDelete(SMS + request.telefone());
            return true;
        }

        String attemptsKey = "SMS_ATTEMPTS:" + request.telefone();

        String attemptsStr = redisTemplate.opsForValue().get(attemptsKey);
        int attempts = attemptsStr != null ? Integer.parseInt(attemptsStr) : 0;

        if (attempts >= 3) {
            throw new ClienteException(ClienteError.MULTIPLAS_TENTATIVAS_TOKEN.getCodeErro());
        }
        redisTemplate.opsForValue().increment(attemptsKey);
        redisTemplate.expire(attemptsKey, Duration.ofMinutes(15));

        return false;
    }

}
