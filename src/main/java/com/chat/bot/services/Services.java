package com.chat.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chat.bot.services.log.Logger;
import lombok.Getter;

@Component
@Getter
public class Services {
    
    @Autowired
    private Logger log;

    @Autowired
    private ValidationElement validation;

    @Autowired
    private FluxoService fluxoService;

    @Autowired
    private AdmService admService;

    @Autowired
    private UsuariosService usuariosService;;

}
