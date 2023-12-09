package com.chat.bot.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chat.bot.model.entitys.Fluxo;
import com.chat.bot.model.entitys.Usuarios;

@Repository
public interface FluxoRepository extends JpaRepository<Fluxo, Long>{
    Fluxo findByPergunta(String pergunta);
    Fluxo findByUsuarioAndInit(Usuarios usuario, boolean init);
    List<Fluxo> findByInit(boolean init);
    List<Fluxo> findByUsuario(Usuarios usuario);
}
