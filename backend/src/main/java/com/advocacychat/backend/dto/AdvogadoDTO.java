package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdvogadoDTO {
    private Long idUsuario;
    private String nome;
    private String email;
    private String tipoUsuario;
    private Boolean ativo;
    private LocalDateTime criadoEmUsuario;
    private LocalDateTime atualizadoEmUsuario;
    private Long idAdvogado;
    private String oab;
    private String especialidade;
    private LocalDateTime criadoEmAdvogado;
}
