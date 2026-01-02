package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.MensagemDTO;
import com.advocacychat.backend.mapper.MensagemMapper;
import com.advocacychat.backend.model.MensagemModel;
import com.advocacychat.backend.repository.MensagensRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

      /* ===============================
        Buscar paginas de 30 mensagens
      =============================== */

    public List<MensagemDTO> buscarUltimasMensagensPorChat(
            Long chatId,
            int page
    ) {
        PageRequest pageable = PageRequest.of(
                page,                      // página
                30,                        // tamanho
                Sort.by(Sort.Direction.DESC, "id")
        );

        Page<MensagemModel> mensagensPage =
                mensagensRepository.findByChatModel_Id(chatId, pageable);

        List<MensagemModel> mensagens =
                new ArrayList<>(mensagensPage.getContent());

        Collections.reverse(mensagens);

        return mensagens.stream()
                .map(mensagemMapper::modelToDto)
                .collect(Collectors.toList());
    }
}
