package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdvogadoDTO {
    private Integer id;
    private Integer usuarioId;
    private String oab;
    private String especialidade;
    private LocalDateTime criadoEm;
}
