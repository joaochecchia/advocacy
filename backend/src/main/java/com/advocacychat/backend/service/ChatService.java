package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.ChatDTO;
import com.advocacychat.backend.mapper.ChatMapper;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
