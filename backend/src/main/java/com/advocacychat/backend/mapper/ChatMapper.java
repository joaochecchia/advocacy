package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.ChatDTO;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.AdvogadoModel;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public static ChatDTO modelToDto(ChatModel model) {
        if (model == null) return null;

        ChatDTO dto = new ChatDTO();
        dto.setId(model.getId());
        dto.setClienteId(model.getClienteModel() != null ? model.getClienteModel().getId() : null);
        dto.setAdvogadoId(model.getAdvogadoModel() != null ? model.getAdvogadoModel().getId() : null);
        dto.setAtivo(model.getAtivo());
        dto.setCriadoEm(model.getCriadoEm());

        return dto;
    }

    public static ChatModel dtoToModel(ChatDTO dto) {
        if (dto == null) return null;

        ChatModel model = new ChatModel();
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

        model.setAtivo(dto.getAtivo());
        model.setCriadoEm(dto.getCriadoEm());

        return model;
    }
}
