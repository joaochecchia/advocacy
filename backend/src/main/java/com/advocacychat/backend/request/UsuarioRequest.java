package com.advocacychat.backend.request;

import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UsuarioRequest(String nome,
                             String email,
                             String senhaHash,
                             TipoUsuario tipoUsuario,
                             Boolean ativo,
                             LocalDateTime ciradoEm,
                             AdvogadoDTO advogadoDTO,
                             ClienteDTO clienteDTO) { }