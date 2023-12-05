package com.chat.bot.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chat.bot.model.dto.req.NodoFluxoDto;
import com.chat.bot.model.dto.req.NodoFluxoDto.InnerNodoFluxoDto;
import com.chat.bot.model.entitys.Fluxo;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.exceptions.ValidationException;
import com.chat.bot.model.repositories.Repositorys;
import com.chat.bot.services.log.Logger;

@Service
public class FluxoService {

    @Autowired
    private Repositorys repositorys;

    @Autowired
    private Logger log;
    
    public void CreateFluxInLast(Usuarios usuario, NodoFluxoDto dto, Map<Integer, String> mapType){
        if(mapType == null){mapType = new HashMap<>();}
        try {
            UserConteisFluxo(usuario);
            List<Fluxo> fluxo = getFluxoInOrder(usuario);
            createFluxo(dto, usuario, mapType, fluxo.size());
        } catch (ValidationException e) {
            CreateFluxo_0(dto, usuario, mapType);
        }
    }

    public void CreateInPosition(Usuarios usuario, NodoFluxoDto dto, Map<Integer, String> mapType) throws ValidationException{
        if(mapType == null){mapType = new HashMap<>();}
        try {
            UserConteisFluxo(usuario);
            List<Fluxo> fluxo = getFluxoInOrder(usuario);
            reorderFluxoList(fluxo, dto.getPriority());
            createFluxo(dto, usuario, mapType, dto.getPriority());
        } catch (ValidationException e) {
            CreateFluxo_0(dto, usuario, mapType);
        }
    }

    private void UserConteisFluxo(Usuarios usuario) throws ValidationException{
        List<Fluxo> fluxo = getFluxo(usuario);

        if(fluxo.isEmpty()){
            throw new ValidationException("lista vazia");
        }
    }

    private void CreateFluxo_0(NodoFluxoDto dto, Usuarios usuario, Map<Integer, String> map){
        Map<Integer, String> opt = map; 

        for (InnerNodoFluxoDto element : dto.getOptions()) {
            opt.put(element.getOpt(), element.getRes());
        }

        Fluxo fluxo = new Fluxo.Builder()
            .pergunta(dto.getPergunta())
            .resposta(opt)
            .sequecia(0)
            .usuario(usuario)
        .build();

        log.saveLog(fluxo);
        repositorys.getFluxoRepository().save(fluxo);
    }

    private void createFluxo(NodoFluxoDto dto, Usuarios usuario, Map<Integer, String> map, Integer priority){
        Map<Integer, String> opt = map; 

        for (InnerNodoFluxoDto element : dto.getOptions()) {
            opt.put(element.getOpt(), element.getRes());
        }

        Fluxo fluxo = new Fluxo.Builder()
            .pergunta(dto.getPergunta())
            .resposta(opt)
            .sequecia(priority)
            .usuario(usuario)
        .build();

        log.saveLog(fluxo);
        repositorys.getFluxoRepository().save(fluxo);  
    }

    private List<Fluxo> getFluxo(Usuarios usuario){
        return repositorys.getFluxoRepository().findByUsuario(usuario);
    }

    private List<Fluxo> getFluxoInOrder(Usuarios usuario){
        List<Fluxo> fluxo = getFluxo(usuario);
        Collections.sort(fluxo);

        return fluxo;
    }

    private void reorderFluxoList(List<Fluxo> fluxo, Integer priorite){
        if(priorite <= fluxo.size() && priorite >= 0){
            for (int i = 0; i < fluxo.size(); i++) {
                Fluxo thisFluxo = fluxo.get(i);
                if(thisFluxo.getSequecia() == priorite){
                    for (int j = i; j < fluxo.size(); j++) {
                        Integer sequence = fluxo.get(j).getSequecia();
                        sequence++;
                        fluxo.get(j).setSequecia(sequence);
                    }
                }
            }
        }

        repositorys.getFluxoRepository().saveAll(fluxo);
    }
}
