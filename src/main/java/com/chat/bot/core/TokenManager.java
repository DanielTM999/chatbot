package com.chat.bot.core;

import java.util.Optional;

import com.auth0.jwt.algorithms.Algorithm;
import com.chat.bot.model.entitys.Usuarios;


public interface TokenManager {
    String generateToken(Usuarios usuario, Algorithm algorithm);
    String generateToken(Usuarios usuario);
    Optional<String> validateToken(String token,  Algorithm algorithm);
    Optional<String> validateToken(String token);
}
