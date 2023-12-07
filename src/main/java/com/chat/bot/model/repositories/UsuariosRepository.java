package com.chat.bot.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.bot.model.entitys.Credenciais;
import com.chat.bot.model.entitys.Usuarios;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long>{
    Usuarios findByCnpj(String cnpj);  
    Usuarios findByCredenciais(Credenciais credenciais);
    Usuarios findByMainNumber(String mainNumber);
}