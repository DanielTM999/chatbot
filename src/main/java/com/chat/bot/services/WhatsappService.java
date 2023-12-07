package com.chat.bot.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.bot.model.dto.req.WhatsAppBusinessAccountDto;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.exceptions.ValidationException;
import com.chat.bot.services.cache.NumberCash;

@Service
public class WhatsappService {

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

    

    public void next(Optional<Usuarios> user, Map<String, String> itens){
        Integer sequnce = cash.getSequceNow(itens.get("number"));
        String msg = itens.get("message");
        
        try {
            varifySequeci(sequnce);
        } catch (ValidationException e) {
            
        }
    }

    private void varifySequeci(Integer sequnce) throws ValidationException{
        if(sequnce == 0){throw new ValidationException("Valor 0");}
    }

    
    
}
