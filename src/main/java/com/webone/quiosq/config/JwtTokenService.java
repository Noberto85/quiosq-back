package com.webone.quiosq.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private static final String AMERICA_RECIFE = "America/Recife";
    private static final String ROLES = "Roles";
    private static final String SECRET_KEY = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P"; // Chave secreta utilizada para gerar e verificar o token

    private static final String ISSUER = "noberto-api"; // Emissor do token

    public String generateToken(UserDetails user) {
        try {
            // Define o algoritmo HMAC SHA256 para criar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                .withIssuer(ISSUER) // Define o emissor do token
                .withIssuedAt(creationDate()) // Define a data de emissão do token
                .withExpiresAt(expirationDate()) // Define a data de expiração do token
                .withSubject(user.getUsername())
                .withClaim(ROLES, user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority) // Transforma em Strings
                    .collect(Collectors.toList())

                )// Define o assunto do token (neste caso, o nome de usuário)
                .sign(algorithm);
            // Assina o token usando o algoritmo especificado
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {

        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getSubject();

    }

    private Instant creationDate() {
        return ZonedDateTime.now(ZoneId.of(AMERICA_RECIFE)).toInstant();
    }

    private Instant expirationDate() {
        return ZonedDateTime.now(ZoneId.of(AMERICA_RECIFE)).plusHours(1).toInstant();
    }

}
