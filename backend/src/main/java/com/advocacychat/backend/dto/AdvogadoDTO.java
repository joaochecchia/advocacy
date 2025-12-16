package com.advocacychat.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min=8, max=10)
    private String oab;

    @NotBlank
    private String especialidade;
    private LocalDateTime criadoEmAdvogado;
}
