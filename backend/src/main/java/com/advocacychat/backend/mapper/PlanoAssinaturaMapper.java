package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.PlanoAssinaturaDTO;
import com.advocacychat.backend.model.PlanoAssinaturaModel;
import org.springframework.stereotype.Component;

@Component
public class PlanoAssinaturaMapper {

    public static PlanoAssinaturaDTO modelToDto(PlanoAssinaturaModel model) {
        if (model == null) return null;

        PlanoAssinaturaDTO dto = new PlanoAssinaturaDTO();
        dto.setId(model.getId());
        dto.setNome(model.getNome());
        dto.setPreco(model.getPreco());
        dto.setDescricao(model.getDescricao());
        dto.setAtivo(model.getAtivo());
        dto.setCriadoEm(model.getCriadoEm());
        dto.setAtualizadoEm(model.getAtualizadoEm());

        return dto;
    }

    public static PlanoAssinaturaModel dtoToModel(PlanoAssinaturaDTO dto) {
        if (dto == null) return null;

        PlanoAssinaturaModel model = new PlanoAssinaturaModel();
        model.setId(dto.getId());
        model.setNome(dto.getNome());
        model.setPreco(dto.getPreco());
        model.setDescricao(dto.getDescricao());
        model.setAtivo(dto.getAtivo());
        model.setCriadoEm(dto.getCriadoEm());
        model.setAtualizadoEm(dto.getAtualizadoEm());

        return model;
    }
}
