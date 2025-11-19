package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.StatusReuniao;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReuniaoDTO {
    private Integer id;
    private Integer clienteId;
    private Integer advogadoId;
    private LocalDateTime dataHora;
    private StatusReuniao status;
    private LocalDateTime criadoEm;
}
