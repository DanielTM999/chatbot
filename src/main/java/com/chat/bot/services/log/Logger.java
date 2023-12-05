package com.chat.bot.services.log;

public interface Logger {
    void ErrorLog(Object message, Class<?> classe);
    void infoLog(Object message, Class<?> classe);
    void saveLog(Object message, Class<?> classe);
    void ErrorLog(Object message);
    void infoLog(Object message);
    void saveLog(Object message);
}
