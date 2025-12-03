package com.advocacychat.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDTO {
    private Long id;
    private Long clienteId;
    private Long advogadoId;
    private Boolean ativo;
    private LocalDateTime criadoEm;
}
