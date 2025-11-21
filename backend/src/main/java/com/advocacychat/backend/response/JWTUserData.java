package com.advocacychat.backend.response;

import lombok.Builder;

@Builder
public record JWTUserData(Integer id, String name, String email) {
}
