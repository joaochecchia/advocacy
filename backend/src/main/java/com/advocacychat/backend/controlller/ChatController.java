package com.advocacychat.backend.controlller;

import com.advocacychat.backend.dto.MensagemDTO;
import com.advocacychat.backend.enums.OrigemMensagem;
import com.advocacychat.backend.request.MessageRequest;
import com.advocacychat.backend.response.JWTUserData;
import com.advocacychat.backend.response.MessageResponse;
import com.advocacychat.backend.service.ChatGPTService;
import com.advocacychat.backend.service.ChatService;
import com.advocacychat.backend.service.MensagensService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {

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
