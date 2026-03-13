package com.advocacychat.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EscritorioDTO {

    private Long id;

    @NotBlank
    private String nomeEscritorio;

    @NotBlank
    private String nomeDono;

    @NotBlank
    @Size(min = 14, max = 14)
    private String cnpj;

    private Long numeroAdvogados;
}