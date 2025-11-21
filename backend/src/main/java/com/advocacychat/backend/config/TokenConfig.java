package com.advocacychat.backend.config;

import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.response.JWTUserData;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalInt;


@Component
public class TokenConfig {

    @Value("${backend.security.secret}")
    private String secret;


    public String gerarToken(UsuarioModel usuarioModel){
        Algorithm algoritimo = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(usuarioModel.getEmail())
                .withClaim("usuarioId", usuarioModel.getId())
                .withClaim("nomeUsuario", usuarioModel.getNome())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("API advocacyChat")
                .sign(algoritimo);
    }

    public Optional<JWTUserData> verifyToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt =  JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData.builder()
                    .id(jwt.getClaim("userId").asInt())
                    .name(jwt.getClaim("name").asString())
                    .email(jwt.getSubject())
                    .build());
        } catch (JWTVerificationException ex){
            return Optional.empty();
        }
    }
}
