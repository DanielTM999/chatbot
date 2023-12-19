package com.chat.bot.model.dto.res;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.chat.bot.model.entitys.Fluxo;
import com.chat.bot.model.entitys.Usuarios;
import lombok.Data;

@Data
public class AcountInfoRes {
    private String cnpj;
    private String mainNumber;
    private String mainDDD;
    private String wtsKey;
    private String wtsToken;
    private String login;
    private List<FluxoInfoAcount> fluxoConversa;

    
    public AcountInfoRes(Optional<Usuarios> user){
        if(user.isPresent()){
            fill(user.get());
        }
    }

    public AcountInfoRes(Usuarios user){
        fill(user);
    }

    private void fill(Usuarios user){
        this.cnpj = user.getCnpj();
        this.mainNumber = user.getMainNumber();
        this.mainDDD = user.getMainDDD();
        this.wtsKey = user.getKeys().getApiKey();
        this.wtsToken = user.getKeys().getApiToken();
        this.login = user.getCredenciais().getUsernameClinte();
        this.fluxoConversa = user.getFluxo()
            .stream()
            .map(FluxoInfoAcount::new)
            .collect(Collectors.toList());
    }


    @Data
    public static class FluxoInfoAcount{
        private Long id;
        private String pergunta;
        private Map<Integer, FluxoInfoAcount> resposta;
        private boolean init;

        public FluxoInfoAcount(Fluxo fluxo){
            this.id = fluxo.getId();
            this.pergunta = fluxo.getPergunta();
            this.resposta = fluxo.getResposta().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new FluxoInfoAcount(entry.getValue())));
            this.init = fluxo.isInit();
        }
    }


}
