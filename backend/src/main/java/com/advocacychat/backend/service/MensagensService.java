package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.MensagemDTO;
import com.advocacychat.backend.mapper.MensagemMapper;
import com.advocacychat.backend.model.MensagemModel;
import com.advocacychat.backend.repository.MensagensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MensagensService {

    private final MensagemMapper mensagemMapper;

    private final MensagensRepository mensagensRepository;

    public Optional<MensagemDTO> registrarMensagem(MensagemDTO request) {
        MensagemModel mensagemModel = mensagemMapper.dtoToModel(request);

        MensagemModel novaMensagem= mensagensRepository.save(mensagemModel);

        return Optional.of(mensagemMapper.modelToDto(novaMensagem));
    }

    public Optional<List<MensagemDTO>> buscarMensagensPorChatId(Long chatId){
        Optional<List<MensagemModel>> mensagens = mensagensRepository.findAllByChatModel_Id(chatId);

        return Optional.of(mensagens.get().stream()
                .map(mensagemMapper::modelToDto)
                .collect(Collectors.toCollection(ArrayList::new)));
    }
}
