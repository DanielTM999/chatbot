package com.chat.bot.model.exceptions;

public class NotFoundElementException extends Exception{
    public NotFoundElementException(String entityName) {
        super("entidade "+entityName+" não encontrada");
    }
}
