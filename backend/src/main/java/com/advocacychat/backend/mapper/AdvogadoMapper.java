package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.UsuarioModel;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class AdvogadoMapper {

    public static AdvogadoDTO modelToDto(AdvogadoModel model) {
        if (model == null) return null;

        AdvogadoDTO dto = new AdvogadoDTO();
        dto.setId(model.getId());
        dto.setUsuarioId(
                model.getUsuarioModel() != null ? model.getUsuarioModel().getId() : null
        );
        dto.setOab(model.getOab());
        dto.setEspecialidade(model.getEspecialidade());
        dto.setCriadoEm(model.getCriadoEm());

        return dto;
    }

    public static AdvogadoModel dtoToModel(AdvogadoDTO dto) {
        if (dto == null) return null;

        AdvogadoModel model = new AdvogadoModel();
        model.setId(dto.getId());
        model.setOab(dto.getOab());
        model.setEspecialidade(dto.getEspecialidade());
        model.setCriadoEm(dto.getCriadoEm());

        if (dto.getUsuarioId() != null) {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setId(dto.getUsuarioId());
            model.setUsuarioModel(usuario);
        }

        return model;
    }
}
