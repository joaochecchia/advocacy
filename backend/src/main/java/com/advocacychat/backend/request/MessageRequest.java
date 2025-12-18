package com.advocacychat.backend.request;

public record MessageRequest(String message, Long chatId, boolean gpt) {
}
