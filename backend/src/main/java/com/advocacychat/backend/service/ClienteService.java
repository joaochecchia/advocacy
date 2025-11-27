package com.advocacychat.backend.service;

import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.mapper.ClienteMapper;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.repository.ClienteRepository;
import com.advocacychat.backend.response.ClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteMapper clienteMapper;

    private final ClienteRepository clienteRepository;

    public Optional<ClienteResponse> findClienteByUserId(Long id){
        Optional<ClienteModel> model = clienteRepository.findByUsuarioModel_Id(id);

        if(model.isEmpty()){
            throw new NotFindObjectByIdentifierException("Usuario com id " + id + " nao existe.");
        }

        return Optional.of(ClienteResponse.fromModel(model.get()));
    }

    public Optional<ClienteResponse> findClienteById(Long id){
        Optional<ClienteModel> model = clienteRepository.findById(id);

        if(model.isEmpty()){
            throw new NotFindObjectByIdentifierException("Cliente com id " + id + " nao existe.");
        }

        return Optional.of(ClienteResponse.fromModel(model.get()));
    }


    public Optional<ClienteResponse> patchClienteById(Long id, ClienteDTO request){
        Optional<ClienteModel> verificarModel = clienteRepository.findById(id);

        if(verificarModel.isEmpty()){
            throw new NotFindObjectByIdentifierException("Cliente com id " + id + " nao existe.");
        }

        request.setId(request.getId());
        request.setUsuarioId(request.getUsuarioId());

        ClienteModel clienteEditado = clienteRepository.save(clienteMapper.dtoToModel(request));

        return Optional.of(ClienteResponse.fromModel(clienteEditado));
    }

    public Long deleteClienteById(Long id){
        Optional<ClienteModel> verificarModel = clienteRepository.findById(id);

        if(verificarModel.isEmpty()){
            throw new NotFindObjectByIdentifierException("Cliente com id " + id + " nao existe.");
        }

        clienteRepository.deleteById(id);

        return id;
    }


}
