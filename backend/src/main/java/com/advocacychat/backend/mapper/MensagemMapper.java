package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.MensagemDTO;
import com.advocacychat.backend.model.*;
import org.springframework.stereotype.Component;

@Component
public class MensagemMapper {

    public static MensagemDTO modelToDto(MensagemModel model) {
        if (model == null) return null;

        MensagemDTO dto = new MensagemDTO();
        dto.setId(model.getId());
        dto.setChatId(model.getChatModel() != null ? model.getChatModel().getId() : null);
        dto.setRemetenteId(model.getRemetente() != null ? model.getRemetente().getId() : null);
        dto.setConteudo(model.getConteudo());
        dto.setOrigem(model.getOrigem());
        dto.setCriadoEm(model.getCriadoEm());

        return dto;
    }

    public static MensagemModel dtoToModel(MensagemDTO dto) {
        if (dto == null) return null;

        MensagemModel model = new MensagemModel();
        model.setId(dto.getId());

        if (dto.getChatId() != null) {
            ChatModel chat = new ChatModel();
            chat.setId(dto.getChatId());
            model.setChatModel(chat);
        }

        if (dto.getRemetenteId() != null) {
            UsuarioModel user = new UsuarioModel();
            user.setId(dto.getRemetenteId());
            model.setRemetente(user);
        }

        model.setConteudo(dto.getConteudo());
        model.setOrigem(dto.getOrigem());
        model.setCriadoEm(dto.getCriadoEm());

        return model;
    }
}
