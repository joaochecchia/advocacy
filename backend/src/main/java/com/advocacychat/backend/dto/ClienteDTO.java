package com.advocacychat.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<Long> idChat;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank
    private String telefone;
    private LocalDateTime criadoEmCliente;
}

