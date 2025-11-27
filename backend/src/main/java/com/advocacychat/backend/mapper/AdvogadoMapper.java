package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.enums.TipoUsuario;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.UsuarioModel;
import org.springframework.stereotype.Component;

@Component
public class AdvogadoMapper {

    public AdvogadoDTO modelToDto(AdvogadoModel model) {
        if (model == null) return null;

        AdvogadoDTO dto = new AdvogadoDTO();
        dto.setIdAdvogado(model.getId());
        dto.setIdUsuario(model.getUsuarioModel() != null ? model.getUsuarioModel().getId() : null);
        dto.setOab(model.getOab());
        dto.setEspecialidade(model.getEspecialidade());
        dto.setCriadoEmAdvogado(model.getCriadoEm());

        if (model.getUsuarioModel() != null) {
            UsuarioModel usuario = model.getUsuarioModel();
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setTipoUsuario(usuario.getTipoUsuario() != null ? usuario.getTipoUsuario().name() : null);
            dto.setAtivo(usuario.getAtivo());
            dto.setCriadoEmUsuario(usuario.getCriadoEm());
            dto.setAtualizadoEmUsuario(usuario.getAtualizadoEm());
        }

        return dto;
    }

    public AdvogadoModel dtoToModel(AdvogadoDTO dto) {
        if (dto == null) return null;

        AdvogadoModel model = new AdvogadoModel();

        model.setId(dto.getIdAdvogado());
        model.setOab(dto.getOab());
        model.setEspecialidade(dto.getEspecialidade());
        model.setCriadoEm(dto.getCriadoEmAdvogado());

        boolean hasUsuarioData = dto.getIdUsuario() != null
                || dto.getNome() != null
                || dto.getEmail() != null
                || dto.getTipoUsuario() != null
                || dto.getAtivo() != null
                || dto.getCriadoEmUsuario() != null
                || dto.getAtualizadoEmUsuario() != null;

        if (hasUsuarioData) {
            UsuarioModel usuario = new UsuarioModel();

            usuario.setId(dto.getIdUsuario());
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : Boolean.TRUE);

            if (dto.getTipoUsuario() != null) {
                try {
                    usuario.setTipoUsuario(TipoUsuario.valueOf(dto.getTipoUsuario()));
                } catch (IllegalArgumentException e) {
                    usuario.setTipoUsuario(null);
                }
            }

            usuario.setCriadoEm(dto.getCriadoEmUsuario());
            usuario.setAtualizadoEm(dto.getAtualizadoEmUsuario());

            usuario.setAdvogado(model);
            model.setUsuarioModel(usuario);
        }

        return model;
    }
}
