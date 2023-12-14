package com.chat.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chat.bot.model.dto.req.UsuarioDto;
import com.chat.bot.model.entitys.Credenciais;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.entitys.WtsKeys;
import com.chat.bot.model.repositories.Repositorys;

@Service
public class AdmService {

    @Autowired
    private Repositorys repositorys;

    public void CreateAndSaveUsuario(UsuarioDto dto){
        Usuarios novo = new Usuarios();
        Credenciais auth = new Credenciais();
        WtsKeys keys = new WtsKeys(); 
        

        novo.setCnpj(dto.getCnpj());
        novo.setMainNumber(dto.getMainNumber());
        novo.setMainDDD(dto.getMainDDD());

        auth.setAdm(false);
        auth.setPlano(dto.getPlano());
        auth.setUsernameClinte(dto.getCnpj());
        auth.setPassword(new BCryptPasswordEncoder().encode(dto.getSenha()));
        auth.setUsuario(novo);

        keys.setApiKey(dto.getApiKey());
        keys.setApiToken(dto.getApiToken());
        keys.setUsuario(novo);

        repositorys.getUsuariosRepository().save(novo);

    }
}
