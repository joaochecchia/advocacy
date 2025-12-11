package com.advocacychat.backend.request;

import java.util.List;

public record ChatGptRequest(String model, List<ChatGptMessageRequest> input) {}
