package com.chat.bot.model.exceptions;

public class ValidationException extends Exception{

    public ValidationException(String msg){
        super(msg);
    }

    public ValidationException(Throwable t){
        super(t);
    }
}
