package com.advocacychat.backend.response;

import lombok.Builder;

@Builder
public record JWTUserData(Long id, String name, String email, String tipo) {
}
