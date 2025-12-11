package com.advocacychat.backend.response;

import java.util.List;

public record ChatGptResponse(
        String id,
        String object,
        long created,
        String model,
        List<Output> output
) {

    public record Output(
            List<Content> content
    ) {}

    public record Content(
            String type,
            String text
    ) {}
}
