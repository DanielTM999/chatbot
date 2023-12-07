package com.chat.bot.services.cache;

import java.time.Instant;

import com.chat.bot.model.entitys.Fluxo;

import lombok.Data;

@Data
public class CashUser {
    private Fluxo fluxo;
    private Instant UtilLife;

    public CashUser(Fluxo fluxo){
        this.fluxo = fluxo;
        this.UtilLife = Instant.now().plusSeconds(60);
    }
}

//24 * 60 * 
