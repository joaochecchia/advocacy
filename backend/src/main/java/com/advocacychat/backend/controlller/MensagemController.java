package com.advocacychat.backend.controlller;

import com.advocacychat.backend.dto.MensagemDTO;
import com.advocacychat.backend.model.MensagemModel;
import com.advocacychat.backend.service.MensagensService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mensagem")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagensService mensagensService;

    @PostMapping("/post")
    public MensagemDTO postMensagem(@RequestBody MensagemDTO mensagemDTO){
        return mensagensService.registrarMensagem(mensagemDTO).get();
    }

    @GetMapping("get/{id}")
    public List<MensagemDTO> getAllMensagem(@PathVariable Long id){
        return mensagensService.buscarMensagensPorChatId(id).get();
    }

    @GetMapping("/chat/{chatId}/mensagens")
    public ResponseEntity<Map<String, Object>> listarMensagens(
            @PathVariable Long chatId,
            @RequestParam(defaultValue = "0") int page
    ) {
        List<MensagemDTO> mensagens =
                mensagensService.buscarUltimasMensagensPorChat(chatId, page);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Últimas 30 mensagens buscadas com sucesso.");
        response.put("Body", mensagens);

        return ResponseEntity.ok(response);
    }
}
