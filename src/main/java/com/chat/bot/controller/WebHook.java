package com.chat.bot.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.chat.bot.model.dto.req.WhatsAppBusinessAccountDto;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.services.CredentialsExtractor;
import com.chat.bot.services.WhatsappService;
import com.chat.bot.services.cache.NumberCash;

@RestController
public class WebHook {

    @Autowired
    private CredentialsExtractor extractor;

    @Autowired
    private NumberCash cash;

    @Autowired
    private WhatsappService whatsappService;
    

    @GetMapping("/webhook/{number}")
    public String webHook(@PathVariable(name = "number") String number, @RequestBody WhatsAppBusinessAccountDto dto){
        Optional<Usuarios> user = extractor.extractDataUserByNumber(number); 
        Map<String, String> msg = whatsappService.getLatestMessageBody(dto);
        String resposta = whatsappService.next(user, msg);
        return resposta;
    }
}
