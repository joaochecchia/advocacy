package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.enums.TipoUsuario;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO modelToDto(ClienteModel model) {
        if (model == null) return null;

        ClienteDTO dto = new ClienteDTO();

        dto.setIdCliente(model.getId());
        dto.setCpf(model.getCpf());
        dto.setTelefone(model.getTelefone());
        dto.setCriadoEmCliente(model.getCriadoEm());

        if (model.getChats() != null && !model.getChats().isEmpty()) {
            dto.setIdChat(
                    model.getChats()
                            .stream()
                            .map(ChatModel::getId)
                            .toList()
            );
        }

        UsuarioModel usuario = model.getUsuarioModel();
        if (usuario != null) {
            dto.setIdUsuario(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());
            dto.setTipoUsuario(
                    usuario.getTipoUsuario() != null
                            ? usuario.getTipoUsuario().name()
                            : null
            );
            dto.setAtivo(usuario.getAtivo());
            dto.setCriadoEmUsuario(usuario.getCriadoEm());
            dto.setAtualizadoEmUsuario(usuario.getAtualizadoEm());
        }

        return dto;
    }

    public ClienteModel dtoToModel(ClienteDTO dto) {
        if (dto == null) return null;

        ClienteModel model = new ClienteModel();

        model.setId(dto.getIdCliente());
        model.setCpf(dto.getCpf());
        model.setTelefone(dto.getTelefone());

        boolean hasUsuarioData =
                dto.getIdUsuario() != null ||
                        dto.getNome() != null ||
                        dto.getEmail() != null ||
                        dto.getTipoUsuario() != null ||
                        dto.getAtivo() != null ||
                        dto.getCriadoEmUsuario() != null ||
                        dto.getAtualizadoEmUsuario() != null;

        if (hasUsuarioData) {
            UsuarioModel usuario = new UsuarioModel();

            usuario.setId(dto.getIdUsuario());
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : Boolean.TRUE);

            if (dto.getTipoUsuario() != null) {
                try {
                    usuario.setTipoUsuario(TipoUsuario.valueOf(dto.getTipoUsuario()));
                } catch (IllegalArgumentException ignored) {
                    usuario.setTipoUsuario(null);
                }
            }

            usuario.setCriadoEm(dto.getCriadoEmUsuario());
            usuario.setAtualizadoEm(dto.getAtualizadoEmUsuario());

            model.setUsuarioModel(usuario);
        }

        return model;
    }
}
