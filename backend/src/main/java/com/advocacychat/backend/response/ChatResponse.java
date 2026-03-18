package com.advocacychat.backend.response;

import com.advocacychat.backend.dto.ChatDTO;

public record ChatResponse(

        ChatDTO chat,
        Long clienteId,
        String clienteNome,
        String ultimaMensagem

) {
}
