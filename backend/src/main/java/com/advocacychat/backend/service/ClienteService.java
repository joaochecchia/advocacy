package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.repository.ClienteRepository;
import com.advocacychat.backend.response.ClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Optional<ClienteResponse> findClienteByUserId(Long id) {
        ClienteModel model = clienteRepository.findByUsuario_Id(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Usuario com id " + id + " nao existe."
                        )
                );

        return Optional.of(ClienteResponse.fromModel(model));
    }

    public Optional<ClienteResponse> findClienteById(Long id) {
        ClienteModel model = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Usuario com id " + id + " nao existe."
                        )
                );

        return Optional.of(ClienteResponse.fromModel(model));
    }

    public Optional<ClienteResponse> patchClienteById(Long id, ClienteDTO request) {
        ClienteModel model = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFindObjectByIdentifierException(
                        "Cliente com id " + id + " nao existe."
                ));

        if (request.getNome() != null) {
            model.setNome(request.getNome());
        }

        if (request.getCpf() != null) {
            model.setCpf(request.getCpf());
        }

        if (request.getTelefone() != null) {
            model.setTelefone(request.getTelefone());
        }

        ClienteModel atualizado = clienteRepository.save(model);

        return Optional.of(ClienteResponse.fromModel(atualizado));
    }

    public Long deleteClienteById(Long id) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Cliente com id " + id + " nao existe."
                        )
                );

        cliente.setUsuario(null);

        clienteRepository.delete(cliente);

        return id;
    }
}
