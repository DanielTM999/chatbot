package com.chat.bot.model.dto.req;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioDto {
    @NotEmpty(message = "valor invalida para cnpj")
    private String cnpj;

    @NotEmpty(message = "valor invalida para mainNumber")
    private String mainNumber;

    @NotEmpty(message = "valor invalida para mainDDD")
    private String mainDDD;

    @NotEmpty(message = "valor invalida para senha")
    private String senha;

    private String ApiKey;

    @NotEmpty(message = "valor invalida para ApiToken")
    private String ApiToken;

    @NotNull(message = "valor invalida para ApiToken")
    private int plano;
}
