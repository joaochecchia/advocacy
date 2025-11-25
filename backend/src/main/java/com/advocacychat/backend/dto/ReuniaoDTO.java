package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.StatusReuniao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReuniaoDTO {
    private Long id;
    private Long clienteId;
    private Long advogadoId;
    private LocalDateTime dataHora;
    private StatusReuniao status;
    private LocalDateTime criadoEm;
}
