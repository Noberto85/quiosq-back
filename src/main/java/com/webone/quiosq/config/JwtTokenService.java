package com.webone.quiosq.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.webone.quiosq.dto.JwtPayload;
import com.webone.quiosq.entity.enums.RoleName;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private static final String AMERICA_RECIFE = "America/Recife";
    private static final String ROLES = "Roles";
    private static final String CLIENTE = "CLIENTE";
    private static final String QUIOSQUE_ID = "quiosque_id";
    private static final String MESA = "mesa";
    private static final String SUB = "sub";
    private static final String ROLE = "role";
    private final String secretKey;
    private static final String ISSUER = "noberto-api";

    public JwtTokenService(@Value("${jwt.secret.key}") String secretKey) {
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


    public String generateClientWithoutExpiration(UUID quiosqueId, Long mesaId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                .withSubject(CLIENTE)
                .withIssuer(ISSUER)
                .withClaim(QUIOSQUE_ID, quiosqueId.toString())
                .withClaim(ROLE, RoleName.ROLE_CLIENTE.name())
                .withClaim(MESA, mesaId)
                .sign(algorithm);
            
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }


    public String getSubjectFromToken(String token) {

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getSubject();
    }

    public JwtPayload parse(String token) {
        try {
            final var claims = JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getClaims();

            JwtPayload p = new JwtPayload();
            p.setSubject(claims.get(SUB).asString());
            p.setMesaId(claims.get(MESA).asInt());
            p.setRole(claims.get(ROLE).asString());
            p.setQuiosqueId(UUID.fromString(claims.get(QUIOSQUE_ID).asString()));
            return p;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of(AMERICA_RECIFE)).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of(AMERICA_RECIFE)).plusHours(1).toInstant();
    }

}
