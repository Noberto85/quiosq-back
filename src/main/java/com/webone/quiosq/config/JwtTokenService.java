package com.webone.quiosq.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.webone.quiosq.dto.ClientDetails;
import com.webone.quiosq.dto.JwtPayload;
import com.webone.quiosq.entity.enums.RoleName;
import com.webone.quiosq.service.MesaService;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private final MesaService mesaService;
    private static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";
    private static final String ROLES = "Roles";
    private static final String CLIENTE = "CLIENTE";
    private static final String QUIOSQUE_ID = "quiosque_id";
    private static final String NUMERO_MESA = "numeroMesa";
    private static final String MESA_ID = "mesaId";
    private static final String SUB = "sub";
    private static final String NOME = "nome";
    private final String secretKey;
    private static final String ISSUER = "noberto-api";

    public JwtTokenService(MesaService mesaService, @Value("${jwt.secret.key}") String secretKey) {
        this.mesaService = mesaService;
        this.secretKey = secretKey;
    }

    public String generateToken(UserDetails user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(creationDate())
                .withExpiresAt(expirationDate())
                .withSubject(user.getUsername())
                .withClaim(ROLES, user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList())
                )
                .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String generateTokenClient(ClientDetails user) {
        Long mesaId = mesaService.findByQuiosqueAndMesa(user.getQuiosqueId(), user.getMesa());
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(creationDate())
                .withExpiresAt(expirationDateClient())
                .withSubject(user.getTelefone())
                .withClaim(NUMERO_MESA, user.getMesa())
                .withClaim(MESA_ID, mesaId)
                .withClaim(QUIOSQUE_ID, user.getQuiosqueId().toString())
                .withClaim(ROLES, Collections.singletonList(RoleName.ROLE_CLIENTE.name()))
                .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }


    public String generateClientWithoutExpiration(UUID quiosqueId, Integer numero, Long mesaId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                .withSubject(CLIENTE)
                .withIssuer(ISSUER)
                .withClaim(QUIOSQUE_ID, quiosqueId.toString())
                .withClaim(ROLES, Collections.singletonList(RoleName.ROLE_CLIENTE.name()))
                .withClaim(NUMERO_MESA, numero)
                .withClaim(MESA_ID, mesaId)
                .sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public JwtPayload parse(String token) {

        final var claims = JWT.require(Algorithm.HMAC256(secretKey))
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getClaims();
        List<String> roles = claims.get(ROLES).asList(String.class);

        JwtPayload p = new JwtPayload();
        if (roles.contains(RoleName.ROLE_CLIENTE.name())) {
            p.setSubject(claims.get(SUB).asString());
            p.setNumeroMesa(claims.get(NUMERO_MESA).asInt());
            p.setMesaId(claims.get(MESA_ID).asLong());
            p.setRole(roles.get(0));
            p.setQuiosqueId(UUID.fromString(claims.get(QUIOSQUE_ID).asString()));
        }
        if (!roles.contains(RoleName.ROLE_CLIENTE.name())) {
            p.setSubject(claims.get(SUB).asString());
        }

        return p;

    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)).plusHours(8).toInstant();
    }

    private Instant expirationDateClient() {
        return ZonedDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)).plusHours(1).toInstant();
    }

}
