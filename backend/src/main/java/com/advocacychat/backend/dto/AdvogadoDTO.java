package com.advocacychat.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AdvogadoDTO {
    private Long id;
    private Long usuarioId;
    private Long escritorioId;

    @NotBlank
    private String nome;

    @NotBlank
    @Size(min = 8, max = 10)
    private String oab;

    private String telefone;
}