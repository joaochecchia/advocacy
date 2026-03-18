package com.advocacychat.backend.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EscritorioRequest(

        @NotBlank
        String nomeEscritorio,

        @NotBlank
        String nomeDono,

        @NotBlank
        @Size(min = 14, max = 14)
        String cnpj,

        Long numeroAdvogados
) { }

