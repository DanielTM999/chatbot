package com.chat.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.chat.bot.services.cache.NumberCash;
import com.chat.bot.services.log.Logger;

@Service
public class ScheduledTaskService  {

    @Autowired
    private Logger log;

    @Scheduled(fixedRate = 5000)
    public void verifyCash() {
        NumberCash.RemoveCashPerTime(log);
    }

}
