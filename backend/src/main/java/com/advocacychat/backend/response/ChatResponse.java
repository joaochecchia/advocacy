package com.advocacychat.backend.response;

import com.advocacychat.backend.dto.ChatDTO;

import java.time.LocalDateTime;

public record ChatResponse(

        ChatDTO chat,
        Long clienteId,
        String clienteNome,
        boolean ativo,
        String ultimaMensagem,
        LocalDateTime dataUltimaMensagem

) {
}
