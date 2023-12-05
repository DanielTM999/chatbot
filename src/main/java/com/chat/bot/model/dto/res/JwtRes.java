package com.chat.bot.model.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JwtRes {
    private String authorizationBarrier;
    private String error;
}
