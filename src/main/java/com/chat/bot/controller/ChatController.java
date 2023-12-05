package com.chat.bot.controller;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chat.bot.model.dto.req.NodoFluxoDto;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.services.CredentialsExtractor;
import com.chat.bot.services.Services;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/new")
public class ChatController {

    @Autowired
    private CredentialsExtractor extractor;

    @Autowired
    private Services services;
    
    @PostMapping("chat/lastelement")
    public void novoElemento(@RequestBody NodoFluxoDto dto, HttpServletRequest request){
        Optional<Usuarios> user = extractor.extractDataUser(request);
        services.getFluxoService().CreateFluxInLast(user.get(), dto, null);
        
    }

}
