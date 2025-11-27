package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.enums.TipoUsuario;
import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.mapper.AdvogadoMapper;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.repository.AdvogadoRepository;
import com.advocacychat.backend.response.AdvogadoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvogadoService {

    private final AdvogadoMapper advogadoMapper;

    private final AdvogadoRepository advogadoRepository;

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


    public Long deleteAdvogadoById(Long id){
        Optional<AdvogadoModel> verificarModel = advogadoRepository.findById(id);

        if(verificarModel.isEmpty()){
            throw new NotFindObjectByIdentifierException("Advogado com id " + id + " nao existe.");
        }

        advogadoRepository.deleteById(id);

        return id;
    }
}
