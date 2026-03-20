package com.advocacychat.backend.request;

import com.advocacychat.backend.dto.AdminDTO;
import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.dto.DonoDTO;
import com.advocacychat.backend.enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UsuarioRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String nome,

        @NotBlank
        @Size(min = 8, max = 15)
        String senhaHash,

        @NotNull
        Role role,

        @NotNull
        Boolean ativo,

        @Valid
        AdminDTO adminDTO,

        @Valid
        DonoDTO donoDTO,

        @Valid
        AdvogadoDTO advogadoDTO,

        @Valid
        ClienteDTO clienteDTO
) { }