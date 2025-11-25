package com.advocacychat.backend.response;

import lombok.Builder;

@Builder
public record UsuarioResponse(Long id, String email) {}
