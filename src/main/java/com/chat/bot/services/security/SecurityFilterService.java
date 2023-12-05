package com.chat.bot.services.security;

import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.chat.bot.core.TokenManager;
import com.chat.bot.model.entitys.Credenciais;
import com.chat.bot.model.repositories.Repositorys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilterService extends OncePerRequestFilter{

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private Repositorys repositorys;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = getToken(request);

        if(token.isPresent()){
            validate(token.get());
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getToken(HttpServletRequest request){
        String token = null;
        token = request.getHeader("Authorization");  
        return Optional.ofNullable(token);
    }

    private void validate(String token){
        Optional<String> isue = tokenManager.validateToken(token);
        if(isue.isPresent()){
            Credenciais credenciais = repositorys.getCredenciaisRepository().findByUsernameClinte(isue.get());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(credenciais, null, credenciais.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
    
}
