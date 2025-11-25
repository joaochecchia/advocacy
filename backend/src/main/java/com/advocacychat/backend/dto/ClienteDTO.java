package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClienteDTO {
    private Long id;
    private Long usuarioId;
    private String cpf;
    private String telefone;
    LocalDateTime criadoEm;
}
