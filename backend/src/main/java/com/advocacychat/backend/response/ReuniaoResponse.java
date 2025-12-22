package com.advocacychat.backend.response;

import com.advocacychat.backend.enums.StatusReuniao;
import com.advocacychat.backend.model.ReuniaoModel;

import java.time.LocalDateTime;

public record ReuniaoResponse(

        Long id,

        /* ===================== CLIENTE ===================== */
        Long clienteId,
        String clienteNome,

        /* ===================== ADVOGADO ===================== */
        Long advogadoId,
        String advogadoNome,

        /* ===================== AGENDA ===================== */
        LocalDateTime dataHora,
        Integer duracaoMinutos,

        /* ===================== STATUS ===================== */
        StatusReuniao status,

        /* ===================== GOOGLE MEET ===================== */
        String googleMeetLink,

        /* ===================== AUDITORIA ===================== */
        LocalDateTime criadoEm
) {

    public static ReuniaoResponse fromModel(ReuniaoModel reuniao) {
        return new ReuniaoResponse(
                reuniao.getId(),

                reuniao.getClienteModel().getId(),
                reuniao.getClienteModel().getUsuarioModel().getNome(),

                reuniao.getAdvogadoModel().getId(),
                reuniao.getAdvogadoModel().getUsuarioModel().getNome(),

                reuniao.getDataHora(),
                reuniao.getDuracaoMinutos(),

                reuniao.getStatus(),

                reuniao.getGoogleMeetLink(),

                reuniao.getCriadoEm()
        );
    }
}