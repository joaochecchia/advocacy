package com.advocacychat.backend.controlller;

import com.advocacychat.backend.dto.ChatDTO;
import com.advocacychat.backend.response.GetAlllChatsResponse;
import com.advocacychat.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/getAllChatsByUserId/{id}")
    public ResponseEntity<Map<String, Object>> buscarChatsPorClienteId(@PathVariable Long id) {
        Optional<List<ChatDTO>> chats = chatService.getAllChatsByClienteId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "chats encontrados com sucesso.");
        response.put("Body", chats.get());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<GetAlllChatsResponse> buscarTodosChats() {
        return ResponseEntity.ok(chatService.getAllChats());
    }
}
