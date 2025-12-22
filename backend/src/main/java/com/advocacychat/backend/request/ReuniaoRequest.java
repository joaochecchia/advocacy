package com.advocacychat.backend.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReuniaoRequest(

        Long clienteId,
        Long advogadoId,

        /**
         * Data e hora de início da reunião
         */
        LocalDateTime dataHora,

        /**
         * Duração da reunião em minutos
         */
        Integer duracaoMinutos
) {
}