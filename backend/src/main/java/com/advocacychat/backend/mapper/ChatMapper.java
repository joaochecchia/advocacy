package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.ChatDTO;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.EscritorioModel;
import org.springframework.stereotype.Component;

@Component
public class ChatMapper {

    public ChatDTO modelToDto(ChatModel model) {
        if (model == null) return null;

        ChatDTO dto = new ChatDTO();
        dto.setId(model.getId());
        dto.setClienteId(model.getCliente() != null ? model.getCliente().getId() : null);
        dto.setDataCriacao(model.getDataCriacao() != null ? model.getDataCriacao() : null);
        dto.setEscritorioId(model.getEscritorio() != null ? model.getEscritorio().getId() : null);

        return dto;
    }

    public ChatModel dtoToModel(ChatDTO dto) {
        if (dto == null) return null;

        ChatModel model = new ChatModel();
        model.setId(dto.getId());

        if (dto.getClienteId() != null) {
            ClienteModel c = new ClienteModel();
            c.setId(dto.getClienteId());
            model.setCliente(c);
        }

        model.setDataCriacao(dto.getDataCriacao() != null ? dto.getDataCriacao() : null);

        if (dto.getEscritorioId() != null) {
            EscritorioModel e = new EscritorioModel();
            e.setId(dto.getEscritorioId());
            model.setEscritorio(e);
        }

        return model;
    }
}
