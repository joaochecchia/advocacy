package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public static ClienteDTO modelToDto(ClienteModel model) {
        if (model == null) return null;

        ClienteDTO dto = new ClienteDTO();
        dto.setId(model.getId());
        dto.setUsuarioId(
                model.getUsuarioModel() != null ? model.getUsuarioModel().getId() : null
        );
        dto.setCpf(model.getCpf());
        dto.setTelefone(model.getTelefone());
        dto.setCriadoEm(model.getCriadoEm());

        return dto;
    }

    public static ClienteModel dtoToModel(ClienteDTO dto) {
        if (dto == null) return null;

        ClienteModel model = new ClienteModel();
        model.setId(dto.getId());
        model.setCpf(dto.getCpf());
        model.setTelefone(dto.getTelefone());
        model.setCriadoEm(dto.getCriadoEm());

        if (dto.getUsuarioId() != null) {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setId(dto.getUsuarioId());
            model.setUsuarioModel(usuario);
        }

        return model;
    }
}
