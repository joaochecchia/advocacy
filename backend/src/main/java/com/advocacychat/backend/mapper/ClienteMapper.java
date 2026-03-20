package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO modelToDto(ClienteModel model) {
        if (model == null) return null;

        ClienteDTO dto = new ClienteDTO();

        dto.setId(model.getId());
        dto.setCpf(model.getCpf());
        dto.setTelefone(model.getTelefone());

        if (model.getChats() != null && !model.getChats().isEmpty()) {
            dto.setChatIds(
                    model.getChats()
                            .stream()
                            .map(ChatModel::getId)
                            .toList()
            );
        }

        if (model.getUsuario() != null) {
            dto.setUsuarioId(model.getUsuario().getId());
        }

        if (model.getEscritorio() != null) {
            dto.setEscritorioId(model.getEscritorio().getId());
        }

        return dto;
    }

    public ClienteModel dtoToModel(ClienteDTO dto) {
        if (dto == null) return null;

        ClienteModel model = new ClienteModel();

        model.setId(dto.getId());
        model.setCpf(dto.getCpf());
        model.setTelefone(dto.getTelefone());

        if (dto.getUsuarioId() != null) {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setId(dto.getUsuarioId());
            model.setUsuario(usuario);
        }

        return model;
    }
}
