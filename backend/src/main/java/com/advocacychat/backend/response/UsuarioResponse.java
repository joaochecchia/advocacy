package com.advocacychat.backend.response;

import lombok.Builder;

@Builder
public record UsuarioResponse(Integer id, String email) {}
