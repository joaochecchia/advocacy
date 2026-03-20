package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.UsuarioDTO;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.request.UsuarioRequest;
import com.advocacychat.backend.response.UsuarioResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO modelToDto(UsuarioModel model) {
        if (model == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(model.getId());
        dto.setEmail(model.getEmail());
        dto.setNome(model.getNome());
        dto.setRole(model.getRole());
        dto.setAtivo(model.getAtivo());

        return dto;
    }

    public UsuarioModel dtoToModel(UsuarioDTO dto) {
        if (dto == null) return null;

        UsuarioModel model = new UsuarioModel();
        model.setId(dto.getId());
        model.setEmail(dto.getEmail());
        model.setNome(dto.getNome());
        model.setRole(dto.getRole());
        model.setAtivo(dto.getAtivo());

        return model;
    }

    public UsuarioResponse dtoToResponse(UsuarioDTO dto){
        if (dto == null) return null;

        return new UsuarioResponse(
                dto.getId(),
                dto.getEmail()
        );
    }

    public UsuarioModel requestToModel(UsuarioRequest request) {

        if (request == null) return null;

        UsuarioModel model = new UsuarioModel();

        model.setEmail(request.email());
        model.setNome(request.nome());
        model.setSenhaHash(request.senhaHash());
        model.setRole(request.role());
        model.setAtivo(request.ativo());

        return model;
    }
}