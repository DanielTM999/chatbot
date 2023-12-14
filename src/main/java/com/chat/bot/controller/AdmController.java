package com.chat.bot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.bot.model.dto.req.UsuarioDto;
import com.chat.bot.model.dto.res.ErrorRes;
import com.chat.bot.model.exceptions.ValidationException;
import com.chat.bot.services.Services;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/adm")
public class AdmController {

    @Autowired
    private Services services;
    
    @PostMapping("/new/cliente")
    public  ResponseEntity<?> novoCliente(@RequestBody @Valid UsuarioDto dto,  BindingResult bindingResult) {
        try {
            services.getValidation().isValid(bindingResult);
            services.getAdmService().CreateAndSaveUsuario(dto);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorRes(e.getMessage()));
        }
    }
    
}
