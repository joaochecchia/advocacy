package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDTO {
    private Integer id;
    private Integer clienteId;
    private Integer advogadoId;
    private Boolean ativo;
    private LocalDateTime criadoEm;
}
