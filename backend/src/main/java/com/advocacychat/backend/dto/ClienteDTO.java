package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClienteDTO {
    private Integer id;
    private Integer usuarioId;
    private String cpf;
    private String telefone;
    LocalDateTime criadoEm;
}
