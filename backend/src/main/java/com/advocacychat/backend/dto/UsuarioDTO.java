package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private Long id;
    private String email;
    private Role role;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Boolean ativo;
}
