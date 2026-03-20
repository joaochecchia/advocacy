package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class AdvogadoMapper {

    public AdvogadoDTO modelToDto(AdvogadoModel model) {
        if (model == null) return null;

        AdvogadoDTO dto = new AdvogadoDTO();

        dto.setId(model.getId());
        dto.setOab(model.getOab());
        dto.setTelefone(model.getTelefone());

        if (model.getUsuario() != null) {
            dto.setUsuarioId(model.getUsuario().getId());
        }

        if (model.getEscritorio() != null) {
            dto.setEscritorioId(model.getEscritorio().getId());
        }

        return dto;
    }

    public AdvogadoModel dtoToModel(AdvogadoDTO dto) {
        if (dto == null) return null;

        AdvogadoModel model = new AdvogadoModel();

        model.setId(dto.getId());
        model.setOab(dto.getOab());
        model.setTelefone(dto.getTelefone());

        if (dto.getUsuarioId() != null) {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setId(dto.getUsuarioId());
            model.setUsuario(usuario);
        }

        return model;
    }
}