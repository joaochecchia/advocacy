package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.StatusReuniao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReuniaoDTO {

    /* ===================== IDENTIFICAÇÃO ===================== */

    private Long id;

    /* ===================== PARTICIPANTES ===================== */

    private Long clienteId;
    private Long advogadoId;

    /* ===================== AGENDA ===================== */

    /**
     * Data e hora de início da reunião.
     * Sempre considerar timezone do servidor.
     */
    private LocalDateTime dataHora;

    /**
     * Duração da reunião em minutos.
     * Ex: 30, 45, 60
     */
    private Integer duracaoMinutos;

    /* ===================== STATUS ===================== */

    private StatusReuniao status;

    /* ===================== GOOGLE MEET / CALENDAR ===================== */

    /**
     * ID do evento no Google Calendar.
     * Usado para sincronização, update e cancelamento.
     */
    private String googleEventId;

    /**
     * Link da reunião no Google Meet.
     */
    private String googleMeetLink;

    /* ===================== AUDITORIA ===================== */

    private LocalDateTime criadoEm;
}