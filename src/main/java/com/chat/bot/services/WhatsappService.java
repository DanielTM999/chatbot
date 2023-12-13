package com.chat.bot.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chat.bot.model.dto.req.WhatsAppBusinessAccountDto;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.exceptions.NotFoundElementException;
import com.chat.bot.services.cache.CashUser;
import com.chat.bot.services.cache.NumberCash;
import com.chat.bot.services.log.Logger;

@Service
public class WhatsappService {
    private String[] reservado = {"redirectTo:|//?|{}", "reset:?", "exit:?"};

    @Autowired
    private NumberCash cash;

    @Autowired
    private Logger log;
    
    
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

    public String next(Optional<Usuarios> user, Map<String, String> itens) throws Exception{
        String number = itens.get("number");
        Integer resposta = Integer.parseInt(itens.get("message"));
        String respostaCliente = "Cliente Ainda não cadastrou o chatbot";
        cash.addOrNextNumberToCash(number, resposta, user);
        Optional<CashUser> cashUser = getCash(number);
        if(cashUser.isPresent()){
            respostaCliente = cashUser.get().getFluxo().getPergunta();
            if(isRedirect(respostaCliente)){
                
            }else if(isReset(respostaCliente)){
                cash.removeExpect(number);
            }
            
        }
        return respostaCliente;
        
    }

    public void SendMessage(String message, Usuarios user, String numberToSend){
        String businessPhoneNumber = user.getMainNumber();
        String authToken = user.getKeys().getApiToken();
        String endpoint = "https://graph.facebook.com/v18.0/" + businessPhoneNumber + "/messages";
        String requestBody = String.format(
            "{"
            + "\"messaging_product\": \"whatsapp\","
            + "\"recipient_type\": \"individual\","
            + "\"to\": \"%s\","
            + "\"type\": \"text\","
            + "\"text\": {\"body\": \"%s\"}"
            + "}",
            numberToSend, message
        );
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
        .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.infoLog("statusCode: " + response.statusCode(), getClass());
        } catch (Exception e) {
            log.ErrorLog(e.getMessage(), getClass());
        }

    }

    private Optional<CashUser> getCash(String key) throws NotFoundElementException{
        CashUser cashUser = cash.getCashUser(key);
        return Optional.ofNullable(cashUser);
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

    private boolean isReset(String value){
        return value.equalsIgnoreCase(reservado[1]);
    }
    
}
