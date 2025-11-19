package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.ReuniaoDTO;
import com.advocacychat.backend.model.*;
import org.springframework.stereotype.Component;

@Component
public class ReuniaoMapper {

    public static ReuniaoDTO modelToDto(ReuniaoModel model) {
        if (model == null) return null;

        ReuniaoDTO dto = new ReuniaoDTO();
        dto.setId(model.getId());
        dto.setClienteId(model.getClienteModel() != null ? model.getClienteModel().getId() : null);
        dto.setAdvogadoId(model.getAdvogadoModel() != null ? model.getAdvogadoModel().getId() : null);
        dto.setDataHora(model.getDataHora());
        dto.setStatus(model.getStatus());
        dto.setCriadoEm(model.getCriadoEm());

        return dto;
    }

    public static ReuniaoModel dtoToModel(ReuniaoDTO dto) {
        if (dto == null) return null;

        ReuniaoModel model = new ReuniaoModel();
        model.setId(dto.getId());

        if (dto.getClienteId() != null) {
            ClienteModel c = new ClienteModel();
            c.setId(dto.getClienteId());
            model.setClienteModel(c);
        }

        if (dto.getAdvogadoId() != null) {
            AdvogadoModel a = new AdvogadoModel();
            a.setId(dto.getAdvogadoId());
            model.setAdvogadoModel(a);
        }

        model.setDataHora(dto.getDataHora());
        model.setStatus(dto.getStatus());
        model.setCriadoEm(dto.getCriadoEm());

        return model;
    }
}
