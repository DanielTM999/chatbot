package com.chat.bot.model.repositories;

import org.springframework.stereotype.Repository;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Repository
public class GenericRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public void excluirGeneric(Long id) {
        String sql = "DELETE FROM fluxo_resposta WHERE resposta_id = :fluxoId";

        entityManager.createNativeQuery(sql)
                .setParameter("fluxoId", id)
                .executeUpdate();
    }
}
