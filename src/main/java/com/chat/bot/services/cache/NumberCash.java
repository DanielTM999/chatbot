package com.chat.bot.services.cache;


import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.chat.bot.services.log.Logger;

@Service
public class NumberCash {
    private static Map<String, CashUser> cash;

    public static void RemoveCashPerTime(Logger log){
        if(cash != null){
            cash.entrySet().removeIf(entry -> remove(entry.getValue(), log, entry.getKey()));
        }
    }

    public void addOrNextNumberToCash(String number){
        instanceMapIfNecessary(new HashMap<>());
        addOrNext(number);
    }

    public Integer getSequceNow(String number){
        instanceMapIfNecessary(new HashMap<>());
        CashUser val = cash.get(number);
        Integer sequnce = 0;
        if(val != null){
            sequnce = val.getSequenceUser();
        }else{
            addOrNextNumberToCash(number);
            sequnce = getSequceNow(number);
        }

        return sequnce;
    }


    private void instanceMapIfNecessary(Map<String, CashUser> maptypecash){
        if(cash == null){
            cash = maptypecash;
        }
    }

    private void addOrNext(String number){
        if(cash.containsKey(number)){
            next(number);
        }else{
            add(number);
        }
    }

    private void add(String number){
        Integer init = 0;
        CashUser newUser = new CashUser(init);
        cash.put(number, newUser);
    }

    private void next(String number){
        Integer sequnce = cash.get(number).getSequenceUser();
        sequnce++;
        cash.get(number).setSequenceUser(sequnce);
    }

    private static boolean remove(CashUser cashUser, Logger log, String number){
        boolean isexpired = cashUser.getUtilLife().isBefore(Instant.now());

        if(isexpired && log != null){
            log.infoLog("removendo clinte "+number+" do cash");
        }

        return isexpired;
    }
}
