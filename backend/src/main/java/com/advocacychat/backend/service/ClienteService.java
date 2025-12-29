package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.enums.TipoUsuario;
import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.repository.ClienteRepository;
import com.advocacychat.backend.response.ClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Optional<ClienteResponse> findClienteByUserId(Long id) {
        ClienteModel model = clienteRepository.findByUsuarioModel_Id(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Usuario com id " + id + " nao existe."
                        )
                );

        return Optional.of(ClienteResponse.fromModel(model));
    }


    public Optional<ClienteResponse> findClienteById(Long id){
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

        if (request.getCpf() != null) {
            model.setCpf(request.getCpf());
        }

        if (request.getTelefone() != null) {
            model.setTelefone(request.getTelefone());
        }

        if (request.getCriadoEmCliente() != null) {
            model.setCriadoEm(request.getCriadoEmCliente());
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

        ClienteModel atualizado = clienteRepository.save(model);

        return Optional.of(ClienteResponse.fromModel(atualizado));
    }


    public Long deleteClienteById(Long id){
        ClienteModel verificarModel = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Cliente com id " + id + " nao existe."
                        )
                );

        clienteRepository.deleteById(id);

        return id;
    }
}
