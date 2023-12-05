package com.chat.bot.model.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.bot.model.entitys.Credenciais;

@Repository
public interface CredenciaisRepository extends JpaRepository<Credenciais, Long>{
    Credenciais findByUsernameClinte(String username);
    List<Credenciais> findByPlano(Integer plano);
}
