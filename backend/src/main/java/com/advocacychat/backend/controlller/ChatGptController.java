package com.advocacychat.backend.controlller;

import com.advocacychat.backend.request.ChatGptMessageRequest;
import com.advocacychat.backend.request.MessageRequest;
import com.advocacychat.backend.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
public class ChatGptController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/ask")
    public String ask(@RequestBody MessageRequest message) {
        return chatGPTService.ask(message.message());
    }
}
