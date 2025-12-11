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

    private final WebClient.Builder webClientBuilder;

    private static final String SYSTEM_PROMPT =
            "Você é um assistente especializado em leis trabalhistas brasileiras. " +
                    "Responda sempre em texto corrido, de forma direta, clara e sucinta.";

    public String sendMessageToChatGPT(String message) {

        WebClient client = webClientBuilder.build();

        ChatGptRequest request = new ChatGptRequest(
                "gpt-4.1-mini",
                List.of(
                        new ChatGptMessageRequest("system", SYSTEM_PROMPT),
                        new ChatGptMessageRequest("user", message)
                )
        );

        ChatGptResponse response = client.post()
                .uri("https://api.openai.com/v1/responses")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChatGptResponse.class)
                .block();

        if (response == null || response.output() == null) {
            return "Nenhuma resposta recebida da OpenAI.";
        }

        return response.output()
                .get(0)
                .content()
                .get(0)
                .text();
    }
}
