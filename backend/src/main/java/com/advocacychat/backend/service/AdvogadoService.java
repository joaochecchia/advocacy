package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.EscritorioModel;
import com.advocacychat.backend.repository.AdvogadoRepository;
import com.advocacychat.backend.repository.EscritorioRepository;
import com.advocacychat.backend.repository.MensagensRepository;
import com.advocacychat.backend.response.AdvogadoResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvogadoService {

    private final AdvogadoRepository advogadoRepository;

    private final EscritorioRepository escritorioRepository;

    private final MensagensRepository mensagensRepository;

    public Optional<AdvogadoResponse> findAdvogadoByUserId(Long id){
        Optional<AdvogadoModel> model = advogadoRepository.findByUsuario_Id(id);

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

        if (request.getNome() != null) {
            model.setNome(request.getNome());
        }

        if (request.getOab() != null) {
            model.setOab(request.getOab());
        }

        if (request.getTelefone() != null) {
            model.setTelefone(request.getTelefone());
        }

        if (request.getEscritorioId() != null) {
            EscritorioModel escritorio = escritorioRepository.findById(request.getEscritorioId())
                    .orElseThrow(() -> new NotFindObjectByIdentifierException(
                            "Escritório com id " + request.getEscritorioId() + " não existe."
                    ));
            model.setEscritorio(escritorio);
        }

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

        Long usuarioId = advogado.getUsuario() != null ? advogado.getUsuario().getId() : null;
        if (usuarioId != null) {
            mensagensRepository.deleteAllByUsuario_Id(usuarioId);
        }

        advogadoRepository.delete(advogado);

        return id;
    }

}
