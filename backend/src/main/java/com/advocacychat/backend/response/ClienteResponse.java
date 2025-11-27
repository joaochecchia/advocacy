package com.advocacychat.backend.response;

import com.advocacychat.backend.model.ClienteModel;

import java.time.LocalDateTime;

public record ClienteResponse(
        Long idUsuario,
        String nome,
        String email,
        String tipoUsuario,
        Boolean ativo,
        LocalDateTime criadoEmUsuario,
        LocalDateTime atualizadoEmUsuario,

        Long idCliente,
        String cpf,
        String telefone,
        LocalDateTime criadoEmCliente
) {
    public static ClienteResponse fromModel(ClienteModel cliente) {
        return new ClienteResponse(
                cliente.getUsuarioModel().getId(),
                cliente.getUsuarioModel().getNome(),
                cliente.getUsuarioModel().getEmail(),
                cliente.getUsuarioModel().getTipoUsuario().name(),
                cliente.getUsuarioModel().getAtivo(),
                cliente.getUsuarioModel().getCriadoEm(),
                cliente.getUsuarioModel().getAtualizadoEm(),

                cliente.getId(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getCriadoEm()
        );
    }
}
