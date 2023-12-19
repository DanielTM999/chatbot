package com.chat.bot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.repositories.Repositorys;

@Service
public class UsuariosService {
   
    @Autowired
    private Repositorys repositorys; 

    public void DeleteUser(Usuarios user){
        repositorys.getUsuariosRepository().delete(user);
    }

    public List<Usuarios> findAll(){
        return repositorys.getUsuariosRepository().findAll();
    }

    public Optional<Usuarios> getBy(String type, String value) throws Exception{
        Optional<Usuarios> user = Optional.empty();
        if(type.equalsIgnoreCase("mainNumber")){
            user = Optional.ofNullable(repositorys.getUsuariosRepository().findByMainNumber(value));
        }else if(type.equalsIgnoreCase("cnpj")){
            user = Optional.ofNullable(repositorys.getUsuariosRepository().findByCnpj(value));
        }else{
            long id = Long.parseLong(value);
            user = repositorys.getUsuariosRepository().findById(id);
        }

        return user;
    }

}
