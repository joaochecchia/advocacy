package com.advocacychat.backend.response;

public record LoginResponse(Long id,
                            String email,
                            String token) { }
