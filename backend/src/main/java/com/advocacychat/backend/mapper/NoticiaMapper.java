package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.NoticiaDTO;
import com.advocacychat.backend.model.NoticiaModel;
import com.advocacychat.backend.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class NoticiaMapper {

    public static NoticiaDTO modelToDto(NoticiaModel model) {
        if (model == null) return null;

        NoticiaDTO dto = new NoticiaDTO();
        dto.setId(model.getId());
        dto.setTitulo(model.getTitulo());
        dto.setConteudo(model.getConteudo());
        dto.setAutorId(model.getAutor() != null ? model.getAutor().getId() : null);
        dto.setPublicadoEm(model.getPublicadoEm());
        dto.setAtivo(model.getAtivo());

        return dto;
    }

    public static NoticiaModel dtoToModel(NoticiaDTO dto) {
        if (dto == null) return null;

        NoticiaModel model = new NoticiaModel();
        model.setId(dto.getId());
        model.setTitulo(dto.getTitulo());
        model.setConteudo(dto.getConteudo());

        if (dto.getAutorId() != null) {
            UsuarioModel u = new UsuarioModel();
            u.setId(dto.getAutorId());
            model.setAutor(u);
        }

        model.setPublicadoEm(dto.getPublicadoEm());
        model.setAtivo(dto.getAtivo());

        return model;
    }
}
