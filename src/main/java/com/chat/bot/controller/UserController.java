package com.chat.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chat.bot.model.dto.req.AuthAdmDto;
import com.chat.bot.model.dto.req.AuthDto;
import com.chat.bot.model.dto.res.JwtRes;
import com.chat.bot.services.Services;
import jakarta.validation.Valid;

@RestController
@RequestMapping("public/user")
public class UserController {

    @Autowired
    private Services services;
    
    @PostMapping("/auth")
    public ResponseEntity<?> authorization(@Valid @RequestBody AuthDto req, BindingResult bindingResult){
        try {
            services.getValidation().isValid(bindingResult);
            String token = services.getValidation().validationAuthCredencial(req.getCnpj(), req.getPassword());
            return ResponseEntity.ok().body(new JwtRes(token, null));
        } catch (Exception e) {
            services.getLog().ErrorLog(e.getMessage(), req.getClass());
            return ResponseEntity.badRequest().body(new JwtRes(null, e.getMessage()));
        }
    }


    @PostMapping("/auth/adm")
    public void authorizationAdm(@Valid @RequestBody AuthAdmDto req, BindingResult bindingResult){

    }

}
