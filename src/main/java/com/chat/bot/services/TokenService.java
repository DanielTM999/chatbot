package com.chat.bot.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.chat.bot.core.TokenManager;
import com.chat.bot.model.entitys.Usuarios;


@Service
public class TokenService implements TokenManager{

    @Value("${api.secret.key}")
    private String secretKey;

    private final Integer EXPIRATE_TIME = 3;
    
    @Override
    public String generateToken(Usuarios usuario, Algorithm algorithm){
        return createToken(usuario, algorithm);
    }

    @Override
    public String generateToken(Usuarios usuario){
        return createToken(usuario, Algorithm.HMAC256(secretKey));
    }

    @Override
    public Optional<String> validateToken(String token, Algorithm algorithm){
        return Valid(token, algorithm);
    }

    @Override
    public Optional<String> validateToken(String token){
        return Valid(token, Algorithm.HMAC256(secretKey));
    }

    private Optional<String> Valid(String token, Algorithm algorithm){
        try{
            Algorithm algorithmToken = algorithm;
            String jwt =  JWT.require(algorithmToken)
                .withIssuer("chatBot-api")
                .build()
                .verify(token)
            .getSubject();

            return Optional.ofNullable(jwt);
        }catch(JWTVerificationException e){
             return Optional.empty();
        }
    }

    private String createToken(Usuarios usuario, Algorithm algorithm){
        try{
            Algorithm algorithmToken = algorithm;
            String token = JWT.create()
                .withIssuer("chatBot-api")
                .withSubject(usuario.getCredenciais().getUsernameClinte())
                .withExpiresAt(expirationDate(EXPIRATE_TIME))
                .sign(algorithmToken);

            return token;
        }catch(JWTCreationException e){
            throw new RuntimeException("erro in "+ e);
        }
    }

    private Instant expirationDate(Integer time){
        return LocalDateTime.now().plusHours(time).toInstant(ZoneOffset.of("-03:00"));
    }

}
