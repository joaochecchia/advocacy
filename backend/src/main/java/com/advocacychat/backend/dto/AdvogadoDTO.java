package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdvogadoDTO {
    private Long id;
    private Long usuarioId;
    private String oab;
    private String especialidade;
    private LocalDateTime criadoEm;
}
