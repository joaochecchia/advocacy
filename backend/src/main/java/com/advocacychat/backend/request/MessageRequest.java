package com.advocacychat.backend.request;

import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.dto.UsuarioDTO;

public record MessageRequest(String nomeRemetente, String message) {
}
