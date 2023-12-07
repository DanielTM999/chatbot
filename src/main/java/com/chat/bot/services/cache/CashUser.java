package com.chat.bot.services.cache;

import java.time.Instant;

import lombok.Data;

@Data
public class CashUser {
    private Integer sequenceUser;
    private Instant UtilLife;

    public CashUser(Integer sequenceUser){
        this.sequenceUser = sequenceUser;
        this.UtilLife = Instant.now().plusSeconds(60);
    }
}

//24 * 60 * 
