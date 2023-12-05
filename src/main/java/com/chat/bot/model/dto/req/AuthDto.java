package com.chat.bot.model.dto.req;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthDto {

    @NotEmpty(message = "O CNPJ não pode estar vazio")
    private String cnpj;

    @NotEmpty(message = "A Senha não pode estar vazia")
    private String password;
}
