package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.UsuarioDTO;
import com.advocacychat.backend.enums.TipoUsuario;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.request.UsuarioRequest;
import com.advocacychat.backend.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final AdvogadoMapper advogadoMapper;

    private final ClienteMapper clienteMapper;

    public UsuarioDTO modelToDto(UsuarioModel model) {
        if (model == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(model.getId());
        dto.setNome(model.getNome());
        dto.setEmail(model.getEmail());
        dto.setSenhaHash(model.getSenhaHash());
        dto.setTipoUsuario(model.getTipoUsuario());
        dto.setAtivo(model.getAtivo());
        dto.setCriadoEm(model.getCriadoEm() != null ? model.getCriadoEm(): null);
        dto.setAtualizadoEm(model.getAtualizadoEm() != null ? model.getAtualizadoEm() : null);

        return dto;
    }

    public UsuarioModel dtoToModel(UsuarioDTO dto) {
        if (dto == null) return null;

        UsuarioModel model = new UsuarioModel();
        model.setId(dto.getId());
        model.setNome(dto.getNome());
        model.setEmail(dto.getEmail());
        model.setSenhaHash(dto.getSenhaHash());
        model.setTipoUsuario(dto.getTipoUsuario());
        model.setAtivo(dto.getAtivo());

        return model;
    }

    public UsuarioResponse dtoToResponse(UsuarioDTO dto){
        if (dto == null) return null;

        return new UsuarioResponse(
                dto.getId(),
                dto.getEmail()
        );
    }

    public UsuarioModel requestToModel(UsuarioRequest request) {
        if (request == null) return null;

        UsuarioModel model = new UsuarioModel();
        model.setNome(request.nome());
        model.setEmail(request.email());
        model.setSenhaHash(request.senhaHash());
        model.setTipoUsuario(request.tipoUsuario());
        model.setAtivo(request.ativo());

        if (model.getTipoUsuario() == TipoUsuario.CLIENTE) {
            ClienteModel cliente = clienteMapper.dtoToModel(request.clienteDTO());
            cliente.setUsuarioModel(model);
            model.setCliente(cliente);
        }

        if (model.getTipoUsuario() == TipoUsuario.ADVOGADO) {
            AdvogadoModel advogado = advogadoMapper.dtoToModel(request.advogadoDTO());
            advogado.setUsuarioModel(model);
            model.setAdvogado(advogado);
        }

        return model;
    }

}
