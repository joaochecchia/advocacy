package com.advocacychat.backend.response;

import com.advocacychat.backend.enums.Role;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.model.ClienteModel;

import java.time.LocalDateTime;
import java.util.List;

public record ClienteResponse(
        Long usuarioId,
        String nome,
        String email,
        Role role,
        Boolean ativo,

        Long clienteId,
        String cpf,
        String telefone,

        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao,

        List<Long> chatIds
) {
    public static ClienteResponse fromModel(ClienteModel cliente) {
        return new ClienteResponse(
                cliente.getUsuario() != null ? cliente.getUsuario().getId() : null,
                cliente.getUsuario() != null ? cliente.getUsuario().getNome() : null,
                cliente.getUsuario() != null ? cliente.getUsuario().getEmail() : null,
                cliente.getUsuario() != null ? cliente.getUsuario().getRole() : null,
                cliente.getUsuario() != null ? cliente.getUsuario().getAtivo() : null,

                cliente.getId(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getUsuario() != null ? cliente.getUsuario().getDataCriacao() : null,
                cliente.getUsuario() != null ? cliente.getUsuario().getDataAtualizacao(): null,

                cliente.getChats()
                        .stream()
                        .map(ChatModel::getId)
                        .toList()
        );
    }
}
