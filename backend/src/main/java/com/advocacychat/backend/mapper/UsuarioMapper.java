package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.UsuarioDTO;
import com.advocacychat.backend.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public static UsuarioDTO modelToDto(UsuarioModel model) {
        if (model == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(model.getId());
        dto.setNome(model.getNome());
        dto.setEmail(model.getEmail());
        dto.setTipoUsuario(model.getTipoUsuario());
        dto.setAtivo(model.getAtivo());
        dto.setCriadoEm(model.getCriadoEm() != null ? model.getCriadoEm(): null);
        dto.setAtualizadoEm(model.getAtualizadoEm() != null ? model.getAtualizadoEm() : null);

        return dto;
    }

    public static UsuarioModel dtoToModel(UsuarioDTO dto) {
        if (dto == null) return null;

        UsuarioModel model = new UsuarioModel();
        model.setId(dto.getId());
        model.setNome(dto.getNome());
        model.setEmail(dto.getEmail());
        model.setTipoUsuario(dto.getTipoUsuario());
        model.setAtivo(dto.getAtivo());

        return model;
    }
}
