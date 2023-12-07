package com.chat.bot.controller;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chat.bot.model.dto.req.NodoFluxoDto;
import com.chat.bot.model.dto.res.ErrorRes;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.exceptions.ValidationException;
import com.chat.bot.services.CredentialsExtractor;
import com.chat.bot.services.Services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private CredentialsExtractor extractor;

    @Autowired
    private Services services;
    
    
    @PostMapping("/new/inposition")
    public ResponseEntity<?> novoElementoInPosition(@RequestBody @Valid NodoFluxoDto dto, BindingResult bindingResult, HttpServletRequest request){
        try {
            services.getValidation().isValid(bindingResult);
            Optional<Usuarios> user = extractor.extractDataUser(request);
            services.getFluxoService().CreateInPosition(user.get(), dto, null);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorRes(e.getMessage()));
        }
    }

    @DeleteMapping("/del/chat/{id}")
    public ResponseEntity<?> deleteByid(@PathVariable String id, HttpServletRequest request){
        Optional<Usuarios> user = extractor.extractDataUser(request);
        try {
            services.getFluxoService().DeleteFromFluxo(user.get(), Long.parseLong(id));
            return ResponseEntity.ok().build();
        } catch (NumberFormatException | ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorRes(e.getMessage()));
        }
        
    }

}
