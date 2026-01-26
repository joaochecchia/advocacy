package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.enums.TipoUsuario;
import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.mapper.AdvogadoMapper;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.repository.AdvogadoRepository;
import com.advocacychat.backend.repository.ChatRepository;
import com.advocacychat.backend.repository.MensagensRepository;
import com.advocacychat.backend.response.AdvogadoResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvogadoService {

    private final AdvogadoMapper advogadoMapper;

    private final AdvogadoRepository advogadoRepository;

    private final MensagensRepository mensagensRepository;

    private final ChatRepository chatRepository;

    public Optional<AdvogadoResponse> findAdvogadoByUserId(Long id){
        Optional<AdvogadoModel> model = advogadoRepository.findByUsuarioModel_Id(id);

        if(model.isEmpty()){
            throw new NotFindObjectByIdentifierException("Advogado com id " + id + " nao existe.");
        }

        return Optional.of(AdvogadoResponse.fromModel(model.get()));
    }

    public Optional<AdvogadoResponse> findAdvogadoById(Long id){
        Optional<AdvogadoModel> model = advogadoRepository.findById(id);

        if(model.isEmpty()){
            throw new NotFindObjectByIdentifierException("Advogado com id " + id + " nao existe.");
        }

        return Optional.of(AdvogadoResponse.fromModel(model.get()));
    }


    public Optional<AdvogadoResponse> patchAdvogadoById(Long id, AdvogadoDTO request) {
        AdvogadoModel model = advogadoRepository.findById(id)
                .orElseThrow(() -> new NotFindObjectByIdentifierException(
                        "Advogado com id " + id + " nao existe."
                ));

        if (request.getOab() != null) {
            model.setOab(request.getOab());
        }

        if (request.getEspecialidade() != null) {
            model.setEspecialidade(request.getEspecialidade());
        }

        if (request.getCriadoEmAdvogado() != null) {
            model.setCriadoEm(request.getCriadoEmAdvogado());
        }

        UsuarioModel usuario = model.getUsuarioModel();

        if (request.getNome() != null) {
            usuario.setNome(request.getNome());
        }

        if (request.getEmail() != null) {
            usuario.setEmail(request.getEmail());
        }

        if (request.getTipoUsuario() != null) {
            usuario.setTipoUsuario(TipoUsuario.valueOf(request.getTipoUsuario()));
        }

        if (request.getAtivo() != null) {
            usuario.setAtivo(request.getAtivo());
        }

        usuario.setAtualizadoEm(LocalDateTime.now());

        AdvogadoModel atualizado = advogadoRepository.save(model);

        return Optional.of(AdvogadoResponse.fromModel(atualizado));
    }


    @Transactional
    public Long deleteAdvogadoById(Long id) {

        AdvogadoModel advogado = advogadoRepository.findById(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Advogado com id " + id + " nao existe."
                        )
                );

        UsuarioModel usuario = advogado.getUsuarioModel();
        Long usuarioId = usuario.getId();

        List<ChatModel> chats = chatRepository.findAllByAdvogadoModel_Id(id);

        mensagensRepository.deleteAllByRemetente_Id(usuarioId);

        if (chats != null && !chats.isEmpty()) {
            chatRepository.deleteAll(chats);
        }

        advogadoRepository.delete(advogado);

        return id;
    }

}
