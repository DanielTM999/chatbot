package com.chat.bot.services.cache;


import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chat.bot.model.entitys.Fluxo;
import com.chat.bot.services.log.Logger;

@Service
public class NumberCash {
    private static Map<String, CashUser> cash;

    public static void RemoveCashPerTime(Logger log){
        if(cash != null){
            cash.entrySet().removeIf(entry -> remove(entry.getValue(), log, entry.getKey()));
        }
    }

    public void addOrNextNumberToCash(String number, Integer next){
        instanceMapIfNecessary(new HashMap<>());
        addOrNext(number, next);
    }

    public Optional<Fluxo> getSequceNow(String number){
        instanceMapIfNecessary(new HashMap<>());
        CashUser val = cash.get(number);
        Fluxo fluxo = null;
        if(val != null){
            fluxo = val.getFluxo();
        }

        return Optional.ofNullable(fluxo);
    }

    private void instanceMapIfNecessary(Map<String, CashUser> maptypecash){
        if(cash == null){
            cash = maptypecash;
        }
    }

    private void addOrNext(String number, Integer next){
        if(cash.containsKey(number)){
            next(number, next);
        }else{
            add(number);
        }
    }

    //add o fluxo(buscar por init)
    private void add(String number){
        Fluxo fluxo = null;
        CashUser newUser = new CashUser(fluxo);
        cash.put(number, newUser);
    }

    private void next(String number, Integer next){
        if(cash.get(number).getFluxo().getResposta().containsKey(next)){       
            Fluxo fluxo = cash.get(number).getFluxo().getResposta().get(next);
            cash.get(number).setFluxo(fluxo);
        }
    }

    private static boolean remove(CashUser cashUser, Logger log, String number){
        boolean isexpired = cashUser.getUtilLife().isBefore(Instant.now());

        if(isexpired && log != null){
            log.infoLog("removendo clinte "+number+" do cash");
        }

        return isexpired;
    }
}
