package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClienteDTO {
    private Long idUsuario;
    private String nome;
    private String email;
    private String tipoUsuario;
    private Boolean ativo;
    private LocalDateTime criadoEmUsuario;
    private LocalDateTime atualizadoEmUsuario;
    private Long idCliente;
    private String cpf;
    private String telefone;
    private LocalDateTime criadoEmCliente;
}

