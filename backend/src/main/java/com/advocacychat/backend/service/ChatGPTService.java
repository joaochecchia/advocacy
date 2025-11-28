package com.advocacychat.backend.service;

import com.advocacychat.backend.request.ChatGptMessageRequest;
import com.advocacychat.backend.request.ChatGptRequest;
import com.advocacychat.backend.response.ChatGptResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/chat/completions")
            .defaultHeader("Content-Type", "application/json")
            .build();

    public String ask(String userMessage) {

        ChatGptRequest request = new ChatGptRequest(
                "gpt-4.1",
                List.of(
                        new ChatGptMessageRequest(
                                "system",
                                "Você é um assistente jurídico especializado em direito brasileiro. " +
                                        "Explique sempre de maneira clara e cite leis quando necessário."
                        ),
                        new ChatGptMessageRequest(
                                "user",
                                userMessage
                        )
                )
        );

        ChatGptResponse response = webClient.post()
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGptResponse.class)
                .block();

        return response.choices().get(0).message().content();
    }
}

