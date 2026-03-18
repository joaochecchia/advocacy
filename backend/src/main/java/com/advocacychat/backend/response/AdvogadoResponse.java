package com.advocacychat.backend.response;

import com.advocacychat.backend.enums.Role;
import com.advocacychat.backend.model.AdvogadoModel;

public record AdvogadoResponse(
        Long usuarioId,
        String email,
        Role role,
        Boolean ativo,

        Long advogadoId,
        String nome,
        String oab,
        String telefone,

        Long escritorioId,
        String escritorioNome
) {

    public static AdvogadoResponse fromModel(AdvogadoModel advogado) {
        Long escritorioId = null;
        String escritorioNome = null;
        if (advogado.getEscritorio() != null) {
            escritorioId = advogado.getEscritorio().getId();
            escritorioNome = advogado.getEscritorio().getNomeEscritorio();
        }
        return new AdvogadoResponse(
                advogado.getUsuario() != null ? advogado.getUsuario().getId() : null,
                advogado.getUsuario() != null ? advogado.getUsuario().getEmail() : null,
                advogado.getUsuario() != null ? advogado.getUsuario().getRole() : null,
                advogado.getUsuario() != null ? advogado.getUsuario().getAtivo() : null,

                advogado.getId(),
                advogado.getNome(),
                advogado.getOab(),
                advogado.getTelefone(),

                escritorioId,
                escritorioNome
        );
    }
}

