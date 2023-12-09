package com.chat.bot.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chat.bot.model.dto.req.WhatsAppBusinessAccountDto;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.exceptions.NotFoundElementException;
import com.chat.bot.model.exceptions.ValidationException;
import com.chat.bot.services.cache.CashUser;
import com.chat.bot.services.cache.NumberCash;

@Service
public class WhatsappService {
    private String[] reservado = {"redirectTo:|//?|{}"};

    @Autowired
    private NumberCash cash;
    
    public Map<String, String> getLatestMessageBody(WhatsAppBusinessAccountDto dto) {
        Map<String, String> body = new HashMap<>();
        if (dto != null && dto.getEntry() != null) {
            for (WhatsAppBusinessAccountDto.Entry entry : dto.getEntry()) {
                if (entry.getChanges() != null) {
                    for (WhatsAppBusinessAccountDto.Change change : entry.getChanges()) {
                        if (change.getValue() != null) {
                            List<WhatsAppBusinessAccountDto.Message> messages = change.getValue().getMessages();
                            if (messages != null && !messages.isEmpty()) {
                                WhatsAppBusinessAccountDto.Message latestMessage = messages.get(messages.size() - 1);
                                String number = latestMessage.getFrom();
                                String message = latestMessage.getText().getBody();
                                body.put("number", number);
                                body.put("message", message);
                            }
                        }
                    }
                }
            }
        }
        return body;
    }

    

    public String next(Optional<Usuarios> user, Map<String, String> itens){
        String number = itens.get("number");
        Integer resposta = Integer.parseInt(itens.get("message"));
        try {
            cash.addOrNextNumberToCash(number, resposta, user);
            CashUser cashUser = cash.getCashUser(number);
            String respostaCliente = cashUser.getFluxo().getPergunta();
            if(isRedirect(respostaCliente)){
                //fazer o redirecionamento(Faço depois)
            }
            return cashUser.getFluxo().getPergunta();
        } catch (NotFoundElementException | ValidationException e) {
            return e.getMessage();
        }
        
    }
    
    private boolean isRedirect(String value){
        int InitString = 0;
        int FinalString = 14;
        try {
            String extract = value.substring(InitString, FinalString);
            String expected = reservado[0].substring(InitString, FinalString);
            return extract.equalsIgnoreCase(expected);
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }
    
}
