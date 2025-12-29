package com.advocacychat.backend.controlller;

import com.advocacychat.backend.request.MessageRequest;
import com.advocacychat.backend.response.JWTUserData;
import com.advocacychat.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;

    @MessageMapping("/new-message/{chatId}")
    public void newMessage(
            @DestinationVariable Long chatId,
            MessageRequest request,
            @Header("simpSessionAttributes") Map<String, Object> sessionAttrs
    ) {

        JWTUserData usuario = (JWTUserData) sessionAttrs.get("user");

        chatService.processarNovaMensagem(
                chatId,
                usuario,
                request
        );
    }
}
