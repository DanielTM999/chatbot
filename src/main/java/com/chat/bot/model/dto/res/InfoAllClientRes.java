package com.chat.bot.model.dto.res;

import java.util.List;
import java.util.stream.Collectors;

import com.chat.bot.model.entitys.Usuarios;

import lombok.Data;

@Data
public class InfoAllClientRes {
    private List<AcountInfoRes> usuarios;

    public InfoAllClientRes(List<Usuarios> usersList){
        usuarios = usersList.stream()
            .map(AcountInfoRes::new)
        .collect(Collectors.toList());
    }
}
