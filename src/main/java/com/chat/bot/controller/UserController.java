package com.chat.bot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.bot.model.dto.res.AcountInfoRes;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.services.CredentialsExtractor;
import com.chat.bot.services.Services;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CredentialsExtractor extractor;

    @Autowired
    private Services services;
    
    @DeleteMapping("/del/acount")
    public void DeleteMyAcount(HttpServletRequest request){
        Optional<Usuarios> user = extractor.extractDataUser(request);
        if(user.isPresent()){
            services.getUsuariosService().DeleteUser(user.get());
        }
    }

    @GetMapping("/info/acount")
    public ResponseEntity<AcountInfoRes> infoAcount(HttpServletRequest request) {
        Optional<Usuarios> user = extractor.extractDataUser(request);
        return ResponseEntity.ok().body(new AcountInfoRes(user));
    }
    

}
