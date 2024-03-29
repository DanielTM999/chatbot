package com.chat.bot.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chat.bot.model.dto.req.NodoFluxoDto;
import com.chat.bot.model.dto.req.NodoFluxoDto.InnerNodoFluxoDto;
import com.chat.bot.model.entitys.Fluxo;
import com.chat.bot.model.entitys.Usuarios;
import com.chat.bot.model.exceptions.ValidationException;
import com.chat.bot.model.repositories.Repositorys;
import com.chat.bot.services.log.Logger;

import jakarta.transaction.Transactional;

@Service
public class FluxoService {

    @Autowired
    private Repositorys repositorys;

    @Autowired
    private Logger log;

    public void CreateInPosition(Usuarios usuario, NodoFluxoDto dto, Map<Integer, Fluxo> mapType) throws ValidationException{
        if(mapType == null){mapType = new HashMap<>();}
        try {
            UserConteisFluxo(usuario);
            createFluxo(dto, usuario, mapType, dto.getPriority());
        } catch (ValidationException e) {
            CreateFluxo_0(dto, usuario, mapType);
        }
    }

    public void DeleteFromFluxo(Usuarios usuario, Long idFluxo) throws ValidationException{
        UserConteisFluxo(usuario);
        delete(usuario, idFluxo);
    }

    private void UserConteisFluxo(Usuarios usuario) throws ValidationException{
        List<Fluxo> fluxo = getFluxo(usuario);

        if(fluxo.isEmpty()){
            throw new ValidationException("lista vazia");
        }
    }

    private void delete(Usuarios usuario, Long idFluxo) throws ValidationException{
        String error = "Fluxo não encontrado";
        Optional<Fluxo> fluxoToDelete = repositorys.getFluxoRepository().findById(idFluxo);
        if(fluxoToDelete.isPresent()){
            Fluxo element = fluxoToDelete.get();
            if(element.getUsuario().equals(usuario)){
                removeAll(element.getResposta());
                deleteThis(element);
                return;
            }else if(element.getUsuario().getCredenciais().isAdm()){
                removeAll(element.getResposta());
                deleteThis(element);
                return;
            }
            error = "você não ten permissao para excluir esse fluxo de dados";
            throw new ValidationException(error);
        }

        throw new ValidationException(error);
    }

    private void CreateFluxo_0(NodoFluxoDto dto, Usuarios usuario, Map<Integer, Fluxo> map){
        Map<Integer, Fluxo> opt = map; 

        for (InnerNodoFluxoDto element : dto.getOptions()) {
            Fluxo nodo = new Fluxo.Builder()
                .pergunta(element.getRes())
                .init(false)
                .usuario(usuario)
            .build();
            repositorys.getFluxoRepository().save(nodo);
            opt.put(element.getOpt(), nodo);
        }

        Fluxo fluxo = createNewFluxo(dto.getPergunta(), opt, usuario, true);

        log.saveLog(fluxo);
        repositorys.getFluxoRepository().save(fluxo);
    }

    private void createFluxo(NodoFluxoDto dto, Usuarios usuario, Map<Integer, Fluxo> map, Long id){
        Map<Integer, Fluxo> opt = map; 
        try {
            verifyID(id, usuario);
            for (InnerNodoFluxoDto element : dto.getOptions()) {
                Fluxo nodo = new Fluxo.Builder()
                    .pergunta(element.getRes())
                    .init(false)
                    .usuario(usuario)
                .build();
                repositorys.getFluxoRepository().save(nodo);
                opt.put(element.getOpt(), nodo);
            }

            Fluxo Newfluxo = createNewFluxo(dto.getPergunta(), opt, usuario, false);
            repositorys.getFluxoRepository().save(Newfluxo);

            Optional<Fluxo> fluxoOption = repositorys.getFluxoRepository().findById(id);
            Fluxo fluxo = fluxoOption.get();
            fluxo.getResposta().put(dto.getOpt(), Newfluxo);
            repositorys.getFluxoRepository().save(fluxo);  

        } catch (ValidationException e) {
            log.ErrorLog(e.getMessage());
        }
    }

    private List<Fluxo> getFluxo(Usuarios usuario){
        return repositorys.getFluxoRepository().findByUsuario(usuario);
    }

    private void verifyID(Long id, Usuarios user) throws ValidationException{

        Optional<Fluxo> element = repositorys.getFluxoRepository().findById(id);

        if(!element.isPresent()){
            throw new ValidationException("sem fluxo");
        }

        Usuarios userfluxo = element.get().getUsuario();
        if(!userfluxo.equals(user)){
            throw new ValidationException("sem fluxo");
        }
        
    }

    private Fluxo createNewFluxo(String pergunta, Map<Integer, Fluxo> opt, Usuarios usuario, boolean init){
        return new Fluxo.Builder()
            .pergunta(pergunta)
            .resposta(opt)
            .init(init)
            .usuario(usuario)
        .build();
    }

    @Transactional()
    private void removeAll(Map<Integer, Fluxo> dta){
        for (Integer chave : dta.keySet()){
            Fluxo del = dta.get(chave);
            if(del.getResposta() == null || del.getResposta().isEmpty()){
                repositorys.getGenericRepository().excluirGeneric(del.getId());
                repositorys.getFluxoRepository().delete(del);
                return;
            }else{
                removeAll(del.getResposta());
            }
        }
    }

    @Transactional()
    private void deleteThis(Fluxo fluxo){
        repositorys.getGenericRepository().excluirGeneric(fluxo.getId());
        repositorys.getFluxoRepository().delete(fluxo);
    }
}
