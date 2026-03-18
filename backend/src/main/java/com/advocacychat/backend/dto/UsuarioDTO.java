package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.Role;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String email;
    private Role role;
    private Boolean ativo;
}
