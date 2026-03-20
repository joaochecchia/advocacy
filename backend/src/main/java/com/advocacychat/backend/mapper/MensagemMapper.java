package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.MensagemDTO;
import com.advocacychat.backend.model.*;
import org.springframework.stereotype.Component;

@Component
public class MensagemMapper {

    public MensagemDTO modelToDto(MensagemModel model) {
        if (model == null) return null;

        MensagemDTO dto = new MensagemDTO();
        dto.setId(model.getId());
        dto.setChatId(model.getChat() != null ? model.getChat().getId() : null);
        dto.setUsuarioId(model.getUsuario() != null ? model.getUsuario().getId() : null);
        dto.setTipo(model.getTipo());
        dto.setConteudo(model.getConteudo());
        dto.setDataCriacao(model.getDataCriacao() != null ? model.getDataCriacao() : null);

        return dto;
    }

    public MensagemModel dtoToModel(MensagemDTO dto) {
        if (dto == null) return null;

        MensagemModel model = new MensagemModel();
        model.setId(dto.getId());

        if (dto.getChatId() != null) {
            ChatModel chat = new ChatModel();
            chat.setId(dto.getChatId());
            model.setChat(chat);
        }

        if (dto.getUsuarioId() != null) {
            UsuarioModel user = new UsuarioModel();
            user.setId(dto.getUsuarioId());
            model.setUsuario(user);
        }

        model.setTipo(dto.getTipo());

        model.setConteudo(dto.getConteudo());

        model.setDataCriacao(dto.getDataCriacao() != null ? dto.getDataCriacao() : null);

        return model;
    }
}
