package com.chat.bot.services.cache;

import java.time.Instant;

import com.chat.bot.model.entitys.Fluxo;

import lombok.Data;

@Data
public class CashUser {
    private final Integer TIMER_TO_INVALID = 24 * 60 * 60;
    private Fluxo fluxo;
    private Instant UtilLife;

    public CashUser(Fluxo fluxo){
        this.fluxo = fluxo;
        this.UtilLife = Instant.now().plusSeconds(TIMER_TO_INVALID);
    }
}
