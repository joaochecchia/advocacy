package com.advocacychat.backend.controlller;

import com.advocacychat.backend.request.MessageRequest;
import com.advocacychat.backend.response.JWTUserData;
import com.advocacychat.backend.response.MessageResponse;
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

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/new-message/{chatId}")
    public void newMessage(
            @DestinationVariable Long chatId,
            MessageRequest request,
            @Header("simpSessionAttributes") Map<String, Object> sessionAttrs
    ) {

        JWTUserData usuario = (JWTUserData) sessionAttrs.get("user");

        validateChatAccess(usuario, chatId);

        MessageResponse response = new MessageResponse(
                request.message()
        );

        messagingTemplate.convertAndSend(
                "/topics/chat/" + chatId,
                response
        );
    }

    private void validateChatAccess(JWTUserData usuario, Long chatId) {

        if (usuario.tipo().equals("CLIENTE")) {
            if (!usuario.id().equals(chatId)) {
                throw new RuntimeException("Cliente não autorizado a acessar outro chat");
            }
            return;
        }

        if (usuario.tipo().equals("ADVOGADO")) {
            return;
        }

        throw new RuntimeException("Tipo de usuário não autorizado");
    }
}
