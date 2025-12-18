package com.advocacychat.backend.request;

import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.enums.TipoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UsuarioRequest(

        @NotBlank
        String nome,

        @Email
        @NotBlank
        String email,

        @NotBlank
        @Size(min = 8, max = 15)
        String senhaHash,

        @NotNull
        TipoUsuario tipoUsuario,

        @NotNull
        Boolean ativo,

        LocalDateTime ciradoEm,

        @Valid
        AdvogadoDTO advogadoDTO,

        @Valid
        ClienteDTO clienteDTO) { }