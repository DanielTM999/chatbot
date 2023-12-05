package com.chat.bot.model.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class Repositorys {
    
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private CredenciaisRepository credenciaisRepository;

    @Autowired
    private FluxoRepository fluxoRepository;

}
