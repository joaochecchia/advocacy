package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.ChatDTO;
import com.advocacychat.backend.dto.MensagemDTO;
import com.advocacychat.backend.enums.OrigemMensagem;
import com.advocacychat.backend.exceptions.UnauthorizedRoleException;
import com.advocacychat.backend.mapper.ChatMapper;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.repository.ChatRepository;
import com.advocacychat.backend.request.MessageRequest;
import com.advocacychat.backend.response.ChatGptResponse;
import com.advocacychat.backend.response.JWTUserData;
import com.advocacychat.backend.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MensagensService mensagensService;

    private final ChatGPTService chatGPTService;

    private final ChatMapper chatMapper;

    private final ChatRepository chatRepository;

    private final SimpMessagingTemplate messagingTemplate;


    public Optional<ChatDTO> registerChat(ChatDTO request) {
        ChatModel chatModel = chatMapper.dtoToModel(request);

        ChatModel novoUsuario = chatRepository.save(chatModel);

        return Optional.of(chatMapper.modelToDto(novoUsuario));
    }

    public void processarNovaMensagem(
            Long chatId,
            JWTUserData usuario,
            MessageRequest request
    ) {

        validateChatAccess(usuario, chatId);

        MensagemDTO mensagemUsuario = criarMensagemUsuario(chatId, usuario, request.message());
        mensagensService.registrarMensagem(mensagemUsuario);

        messagingTemplate.convertAndSend(
                "/topics/chat/" + chatId,
                new MessageResponse(request.message())
        );

        if (request.gpt()) {
            processarMensagemGPT(chatId, usuario, request.message());
        }
    }

    private MensagemDTO criarMensagemUsuario(
            Long chatId,
            JWTUserData usuario,
            String conteudo
    ) {
        MensagemDTO dto = new MensagemDTO();
        dto.setChatId(chatId);
        dto.setRemetenteId(usuario.id());
        dto.setConteudo(conteudo);
        dto.setOrigem(OrigemMensagem.valueOf(usuario.tipo()));
        dto.setCriadoEm(LocalDateTime.now());
        return dto;
    }

    private void processarMensagemGPT(Long chatId, JWTUserData usuario, String conteudo) {

        String respostaGPT = chatGPTService.sendMessageToChatGPT(conteudo);

        MensagemDTO dto = new MensagemDTO();
        dto.setChatId(chatId);
        dto.setRemetenteId(usuario.id());
        dto.setConteudo(respostaGPT);
        dto.setOrigem(OrigemMensagem.GPT);
        dto.setCriadoEm(LocalDateTime.now());

        mensagensService.registrarMensagem(dto);

        messagingTemplate.convertAndSend(
                "/topics/chat/" + chatId,
                new MessageResponse(respostaGPT)
        );
    }

    public void validateChatAccess(JWTUserData usuario, Long chatId) {

        if (usuario.tipo().equals("ADVOGADO")) {
            return;
        }

        if (usuario.tipo().equals("CLIENTE")) {

            boolean pertenceAoCliente = chatRepository
                    .existsByIdAndClienteModel_UsuarioModel_Id(chatId, usuario.id());
            System.out.println("ESTIVE AQUI 1: " + pertenceAoCliente);

            if (!pertenceAoCliente) {
                throw new UnauthorizedRoleException("Cliente não autorizado a acessar este chat");
            }

            return;
        }
        throw new UnauthorizedRoleException("Tipo de usuário não autorizado");
    }


    /**public Optional<List<ChatDTO>> getAllChatsByClienteId(){

    }**/

}
