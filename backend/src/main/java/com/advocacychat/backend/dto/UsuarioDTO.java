package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.TipoUsuario;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioDTO {
    private Integer id;
    private String nome;
    private String email;
    private String senhaHash;
    private TipoUsuario tipoUsuario;
    private Boolean ativo;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
}
