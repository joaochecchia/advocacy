package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.DonoDTO;
import com.advocacychat.backend.model.DonoModel;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.model.EscritorioModel;
import org.springframework.stereotype.Component;

@Component
public class DonoMapper {

    public DonoDTO modelToDto(DonoModel model) {
        if (model == null) return null;

        DonoDTO dto = new DonoDTO();

        dto.setId(model.getId());

        if (model.getTelefone() != null) {
            try {
                dto.setTelefone(model.getTelefone());
            } catch (NumberFormatException e) {
                dto.setTelefone(null);
            }
        }

        if (model.getUsuario() != null) {
            dto.setUsuarioId(model.getUsuario().getId());
        }

        if (model.getEscritorio() != null) {
            dto.setEscritorioId(model.getEscritorio().getId());
        }

        return dto;
    }

    public DonoModel dtoToModel(DonoDTO dto) {
        if (dto == null) return null;

        DonoModel model = new DonoModel();

        model.setId(dto.getId());

        if (dto.getTelefone() != null) {
            model.setTelefone(String.valueOf(dto.getTelefone()));
        }

        if (dto.getUsuarioId() != null) {
            UsuarioModel usuario = new UsuarioModel();
            usuario.setId(dto.getUsuarioId());
            model.setUsuario(usuario);
        }

        if (dto.getEscritorioId() != null) {
            EscritorioModel escritorio = new EscritorioModel();
            escritorio.setId(dto.getEscritorioId());
            model.setEscritorio(escritorio);
        }

        return model;
    }
}