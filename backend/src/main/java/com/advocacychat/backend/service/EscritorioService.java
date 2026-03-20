package com.advocacychat.backend.service;

import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.exceptions.UniqueFieldException;
import com.advocacychat.backend.model.EscritorioModel;
import com.advocacychat.backend.repository.EscritorioRepository;
import com.advocacychat.backend.request.EscritorioRequest;
import com.advocacychat.backend.dto.EscritorioDTO;
import com.advocacychat.backend.response.EscritorioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EscritorioService {

    private final EscritorioRepository escritorioRepository;

    public EscritorioResponse cadastrarEscritorio(EscritorioRequest request) {

        if (escritorioRepository.existsByCnpj(request.cnpj())) {
            throw new UniqueFieldException("Escritório com CNPJ " + request.cnpj() + " já está cadastrado.");
        }

        EscritorioModel model = new EscritorioModel();
        model.setNomeEscritorio(request.nomeEscritorio());
        model.setNomeDono(request.nomeDono());
        model.setCnpj(request.cnpj());
        model.setNumeroAdvogados(request.numeroAdvogados());

        EscritorioModel salvo = escritorioRepository.save(model);

        return EscritorioResponse.fromModel(salvo);
    }

    public Optional<EscritorioResponse> buscarPorId(Long id) {
        EscritorioModel model = escritorioRepository.findById(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Escritório com id " + id + " não existe."
                        )
                );

        return Optional.of(EscritorioResponse.fromModel(model));
    }

    public Optional<EscritorioResponse> editarEscritorio(Long id, EscritorioDTO request) {
        EscritorioModel model = escritorioRepository.findById(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Escritório com id " + id + " não existe."
                        )
                );

        if (request.getNomeEscritorio() != null) {
            model.setNomeEscritorio(request.getNomeEscritorio());
        }

        if (request.getNomeDono() != null) {
            model.setNomeDono(request.getNomeDono());
        }

        if (request.getCnpj() != null) {
            if (!request.getCnpj().equals(model.getCnpj())
                    && escritorioRepository.existsByCnpj(request.getCnpj())) {
                throw new UniqueFieldException("Escritório com CNPJ " + request.getCnpj() + " já está cadastrado.");
            }
            model.setCnpj(request.getCnpj());
        }

        if (request.getNumeroAdvogados() != null) {
            model.setNumeroAdvogados(request.getNumeroAdvogados());
        }

        EscritorioModel atualizado = escritorioRepository.save(model);

        return Optional.of(EscritorioResponse.fromModel(atualizado));
    }

    public Long deletarEscritorio(Long id) {
        EscritorioModel model = escritorioRepository.findById(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Escritório com id " + id + " não existe."
                        )
                );

        escritorioRepository.delete(model);

        return id;
    }
}

