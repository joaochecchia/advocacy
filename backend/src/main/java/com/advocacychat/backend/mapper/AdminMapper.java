package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.AdminDTO;
import com.advocacychat.backend.model.AdminModel;
import com.advocacychat.backend.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDTO modelToDto(AdminModel model) {
        if (model == null) return null;

        AdminDTO dto = new AdminDTO();

        dto.setId(model.getId());

        if (model.getUsuario() != null) {
            dto.setUserId(model.getUsuario().getId());
        }

        return dto;
    }

    public AdminModel dtoToModel(AdminDTO dto) {
        if (dto == null) return null;

        AdminModel model = new AdminModel();

        model.setId(dto.getId());

        if (dto.getUserId() != null) {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setId(dto.getUserId());
            model.setUsuario(usuario);
        }

        return model;
    }
}