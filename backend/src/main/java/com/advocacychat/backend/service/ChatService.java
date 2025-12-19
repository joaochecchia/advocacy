package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.ChatDTO;
import com.advocacychat.backend.exceptions.UnauthorizedRoleException;
import com.advocacychat.backend.mapper.ChatMapper;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.repository.ChatRepository;
import com.advocacychat.backend.response.JWTUserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMapper chatMapper;

    private final ChatRepository chatRepository;

    public Optional<ChatDTO> registerChat(ChatDTO request) {
        ChatModel chatModel = chatMapper.dtoToModel(request);

        ChatModel novoUsuario = chatRepository.save(chatModel);

        return Optional.of(chatMapper.modelToDto(novoUsuario));
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
