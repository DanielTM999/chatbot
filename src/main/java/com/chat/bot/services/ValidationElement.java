package com.chat.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import com.chat.bot.core.TokenManager;
import com.chat.bot.model.entitys.Credenciais;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.exceptions.ValidationException;
import com.chat.bot.model.repositories.Repositorys;

@Service
public class ValidationElement {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Repositorys repositorys;

    @Autowired
    private TokenManager tokenManager;

    public void isValid(BindingResult bindingResult) throws ValidationException{
        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> {
                errors.append(error.getDefaultMessage());
            });

            throw new ValidationException(errors.toString());
        }
    }

    public String validationAuthCredencial(String username, String password) throws ValidationException{
        try {
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(username, password);
            Authentication auth = this.authenticationManager.authenticate(usernamePassword);
            Credenciais credenciais = (Credenciais)auth.getPrincipal();
            Usuarios user = repositorys.getUsuariosRepository().findByCredenciais(credenciais);

            String token = tokenManager.generateToken(user);

            return token;
        } catch (Exception e) {
            throw new ValidationException("Erro na autenticação dados incorretos");
        }
    }
}
