package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.ChatDTO;
import com.advocacychat.backend.dto.MensagemDTO;
import com.advocacychat.backend.enums.OrigemMensagem;
import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.exceptions.NullFieldException;
import com.advocacychat.backend.exceptions.UnauthorizedRoleException;
import com.advocacychat.backend.mapper.ChatMapper;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.repository.ChatRepository;
import com.advocacychat.backend.request.MessageRequest;
import com.advocacychat.backend.response.ChatResponse;
import com.advocacychat.backend.response.GetAlllChatsResponse;
import com.advocacychat.backend.response.JWTUserData;
import com.advocacychat.backend.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final MensagensService mensagensService;

    private final ClienteService clienteService;

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

    public Optional<List<ChatDTO>> getAllChatsByClienteId(Long id){
        clienteService.findClienteById(id).orElseThrow(() -> new NotFindObjectByIdentifierException("Cliente com id " + id + " nao existe."));

        List<ChatModel> chatModels = chatRepository.findAllByClienteModel_Id(id);

        if(chatModels.isEmpty()){
            throw new NotFindObjectByIdentifierException("Cliente com id: " + id + "nao possui chats.");
        }

        return Optional.of(
                chatModels.stream()
                        .map(chatMapper::modelToDto)
                        .collect(Collectors.toCollection(ArrayList::new))
        );
    }

    public GetAlllChatsResponse getAllChats() {

        List<ChatResponse> chats = chatRepository.findAll()
                .stream()
                .map(chat -> {

                    ChatDTO chatDTO = chatMapper.modelToDto(chat);

                    return new ChatResponse(
                            chatDTO,
                            chat.getClienteModel().getId(),
                            chat.getClienteModel().getUsuarioModel().getNome(),
                            chat.getAtivo(),
                            mensagensService.buscarUltimaMensagemPorChatId(chat.getId()),
                            mensagensService.buscarDataUltimaMensagem(chat.getId())
                    );
                })
                .toList();

        return new GetAlllChatsResponse(
                "Todos os chats encontrados com sucesso.",
                chats
        );
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
}
