package com.chat.bot.services.cache;


import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.bot.model.entitys.Fluxo;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.exceptions.NotFoundElementException;
import com.chat.bot.model.exceptions.ValidationException;
import com.chat.bot.model.repositories.Repositorys;
import com.chat.bot.services.log.Logger;

@Service
public class NumberCash {

    @Autowired
    private Repositorys repositorys;

    private static Map<String, CashUser> cash;

    public static void RemoveCashPerTime(Logger log){
        if(cash != null){
            cash.entrySet().removeIf(entry -> remove(entry.getValue(), log, entry.getKey()));
        }
    }

    public void addOrNextNumberToCash(String number, Integer next, Optional<Usuarios> user) throws ValidationException{
        instanceMapIfNecessary(new HashMap<>());
        if(user.isPresent()){
            addOrNext(number, next, user.get());
        }
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

    public CashUser getCashUser(String number) throws NotFoundElementException{
        if(cash.containsKey(number)){
            return cash.get(number);
        }
        throw new NotFoundElementException(number);
    }

    private void instanceMapIfNecessary(Map<String, CashUser> maptypecash){
        if(cash == null){
            cash = maptypecash;
        }
    }

    private void addOrNext(String number, Integer next, Usuarios user) throws ValidationException{
        if(cash.containsKey(number)){
            next(number, next);
        }else{
            add(number, user);
        }
    }

    private void add(String number, Usuarios user){
        Fluxo fluxo = repositorys.getFluxoRepository().findByUsuarioAndInit(user, true);
        CashUser newUser = new CashUser(fluxo);
        cash.put(number, newUser);
    }

    private void next(String number, Integer next) throws ValidationException{
        boolean isFinal = cash.get(number).getFluxo().getResposta().isEmpty();
        if(cash.get(number).getFluxo().getResposta().containsKey(next)){       
            Fluxo fluxo = cash.get(number).getFluxo().getResposta().get(next);
            cash.get(number).setFluxo(fluxo);
        }else if(isFinal){
            throw new ValidationException("Fim da conversa");
        }else{
            throw new ValidationException("resposta invalida");
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
