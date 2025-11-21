package com.advocacychat.backend.request;

import lombok.Builder;

@Builder
public record UsuarioRequest(String email, String senha) {
}
