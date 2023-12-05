package com.chat.bot.model.dto.req;

import java.util.List;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NodoFluxoDto {
    
    @NotEmpty(message = "a pergunta não pode ser vazia")
    private String pergunta;

    @NotEmpty(message = "a Lista de opção não pode ser vazia")
    private List<InnerNodoFluxoDto> options;

    @NotNull(message = "a Priority não pode ser nula")
    private Integer Priority;

    @Data
    @Builder
    public static class InnerNodoFluxoDto {
        @NotNull(message = "a opt não pode ser nula")
        private Integer opt;

        @NotEmpty(message = "a res não pode ser vazia")
        private String res;
    }

}
