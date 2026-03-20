package com.advocacychat.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ClienteDTO {
    private Long id;
    private Long usuarioId;
    private Long escritorioId;

    @NotBlank
    private String telefone;

    private List<Long> chatIds;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;
}

