package com.chat.bot.services.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class Log implements Logger{
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    @Value("${logger.console}")
    public boolean isConsole = false;

    @Override
    public void ErrorLog(Object message, Class<?> classe){
        log(message, classe, InnerLog.ERROR);
    }

    @Override
    public void infoLog(Object message, Class<?> classe){
        log(message, classe, InnerLog.INFO);
    }

    @Override
    public void saveLog(Object message, Class<?> classe) {
        log(message, classe, InnerLog.SAVE);
    }

    @Override
    public void ErrorLog(Object message){
        log(message, message.getClass(), InnerLog.ERROR);
    }

    @Override
    public void infoLog(Object message){
        log(message, message.getClass(), InnerLog.INFO);
    }

    @Override
    public void saveLog(Object message) {
        log(message, message.getClass(), InnerLog.SAVE);
    }

    private void log(Object message, Class<?> classe, InnerLog typelog){
        if(message == null){
            message = "null";
        }
        String path = System.getProperty("user.dir") + "/app.log";
        File file = new File(path);
        Date dataHoraAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String data = formato.format(dataHoraAtual);
        String msgformated = data + ": ["+typelog.toString()+"] --> "+ message.toString() + " : " + classe.getSimpleName();


        if(isConsole){
            System.out.println(msgformated);
        }else{
            if(file.exists()){
                try {
                    BufferedWriter whiter = new BufferedWriter(new FileWriter(file, true));
                    whiter.write("\n"+msgformated);
                    whiter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static enum InnerLog {
        INFO,
        ERROR,
        SAVE  
    }

}
