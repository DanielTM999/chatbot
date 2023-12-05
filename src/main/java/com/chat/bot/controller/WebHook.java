package com.chat.bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chat.bot.services.log.Logger;

@RestController
public class WebHook {

    @Autowired
    private Logger logger;

    @GetMapping("/webhook/{number}")
    public void webHook(@PathVariable(name = "number") String number){
        logger.infoLog(number, getClass());
    }
}
