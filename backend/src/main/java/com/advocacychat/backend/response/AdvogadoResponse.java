package com.advocacychat.backend.response;

import com.advocacychat.backend.model.AdvogadoModel;

import java.time.LocalDateTime;

public record AdvogadoResponse(
        Long idUsuario,
        String nome,
        String email,
        String tipoUsuario,
        Boolean ativo,
        LocalDateTime criadoEmUsuario,
        LocalDateTime atualizadoEmUsuario,

        Long idAdvogado,
        String oab,
        String especialidade,
        LocalDateTime criadoEmAdvogado
) {

    public static AdvogadoResponse fromModel(AdvogadoModel advogado) {
        return new AdvogadoResponse(
                advogado.getUsuarioModel().getId(),
                advogado.getUsuarioModel().getNome(),
                advogado.getUsuarioModel().getEmail(),
                advogado.getUsuarioModel().getTipoUsuario().name(),
                advogado.getUsuarioModel().getAtivo(),
                advogado.getUsuarioModel().getCriadoEm(),
                advogado.getUsuarioModel().getAtualizadoEm(),

                advogado.getId(),
                advogado.getOab(),
                advogado.getEspecialidade(),
                advogado.getCriadoEm()
        );
    }
}

