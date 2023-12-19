package com.chat.bot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chat.bot.model.dto.req.UsuarioDto;
import com.chat.bot.model.dto.res.ErrorRes;
import com.chat.bot.model.dto.res.InfoAllClientRes;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.exceptions.ValidationException;
import com.chat.bot.services.Services;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/info/client/all")
    public ResponseEntity<?> ListAllUser(){
        List<Usuarios> userList = services.getUsuariosService().findAll();
        return ResponseEntity.ok().body(new InfoAllClientRes(userList)); 
    }

    @DeleteMapping("/dell/cliente/{id}")
    public ResponseEntity<?> deleteUserBy(@PathVariable String id, @RequestBody Map<String, String> request){
        String deleteBy = request.get("deleteBy");
        try {
            Optional<Usuarios> user = services.getUsuariosService().getBy(deleteBy, id);
            if(user.isPresent()){
                services.getUsuariosService().DeleteUser(user.get());
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorRes("Ususario não encontrado"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorRes(e.getMessage()));
        }
    }
    
}
