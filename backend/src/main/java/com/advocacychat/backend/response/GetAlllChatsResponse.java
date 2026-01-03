package com.advocacychat.backend.response;

import java.util.List;

public record GetAlllChatsResponse(
        String message,
        List<ChatResponse> body
) {
}
