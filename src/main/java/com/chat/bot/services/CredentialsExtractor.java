package com.chat.bot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chat.bot.core.TokenManager;
import com.chat.bot.model.entitys.Credenciais;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.repositories.Repositorys;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CredentialsExtractor {
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private Repositorys repositorys;


    public Optional<Usuarios> extractDataUser(HttpServletRequest request){
        Optional<String> token = extractToken(request);
        return validate(token.get());
    }

    private Optional<String> extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return Optional.ofNullable(token);
    }

    private Optional<Usuarios> validate(String token){
        Optional<String> isue = tokenManager.validateToken(token);
        if(isue.isPresent()){
            Credenciais credenciais = repositorys.getCredenciaisRepository().findByUsernameClinte(isue.get());
            Usuarios user = repositorys.getUsuariosRepository().findByCredenciais(credenciais);
            return Optional.ofNullable(user);
        }

        return Optional.empty();
    }
}
