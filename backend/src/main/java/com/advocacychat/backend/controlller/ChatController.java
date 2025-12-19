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

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatGPTService chatGPTService;

    private final ChatService chatService;

    private final MensagensService mensagensService;

    @MessageMapping("/new-message/{chatId}")
    public void newMessage(
            @DestinationVariable Long chatId,
            MessageRequest request,
            @Header("simpSessionAttributes") Map<String, Object> sessionAttrs
    ) {

        JWTUserData usuario = (JWTUserData) sessionAttrs.get("user");

        chatService.validateChatAccess(usuario, chatId);

        MessageResponse response = new MessageResponse(
                request.message()
        );

        MensagemDTO mensagemDTO = new MensagemDTO();
        mensagemDTO.setChatId(chatId);
        mensagemDTO.setRemetenteId(usuario.id());
        mensagemDTO.setConteudo(request.message());

        if(usuario.tipo().equals("CLIENTE")) mensagemDTO.setOrigem(OrigemMensagem.CLIENTE);

        if(usuario.tipo().equals("ADVOGADO")) mensagemDTO.setOrigem(OrigemMensagem.ADVOGADO);

        mensagemDTO.setCriadoEm(LocalDateTime.now());

        mensagensService.registrarMensagem(mensagemDTO);

        messagingTemplate.convertAndSend(
                "/topics/chat/" + chatId,
                response
        );

        if(request.gpt()){
            String chatGptMessage = chatGPTService.sendMessageToChatGPT(request.message());

            MessageResponse chatGptResponse = new MessageResponse(
                    chatGptMessage
            );

            MensagemDTO gptMensagemDTO = new MensagemDTO();
            gptMensagemDTO.setChatId(chatId);
            //deppois tirar remetente nao nulo
            gptMensagemDTO.setRemetenteId(usuario.id());
            gptMensagemDTO.setConteudo(chatGptMessage);
            gptMensagemDTO.setOrigem(OrigemMensagem.GPT);
            gptMensagemDTO.setCriadoEm(LocalDateTime.now());

            mensagensService.registrarMensagem(gptMensagemDTO);

            messagingTemplate.convertAndSend(
                    "/topics/chat/" + chatId,
                    chatGptResponse
            );
        }
    }
}
