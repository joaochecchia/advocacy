package com.advocacychat.backend.controlller;

import com.advocacychat.backend.request.ReuniaoRequest;
import com.advocacychat.backend.response.ReuniaoResponse;
import com.advocacychat.backend.service.ReuniaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reuniao")
@RequiredArgsConstructor
public class ReuniaoController {

    private final ReuniaoService reuniaoService;

    /* ===================== CREATE ===================== */

    @PostMapping("/criar")
    public ResponseEntity<Map<String, Object>> criarReuniao(
            @RequestBody ReuniaoRequest request
    ) {
        Optional<ReuniaoResponse> reuniao = reuniaoService.criarReuniao(request);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Reuniao criada com sucesso.");
        response.put("Body", reuniao.get());

        return ResponseEntity.ok(response);
    }

    /* ===================== READ ===================== */

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> buscarReuniaoPorId(
            @PathVariable Long id
    ) {
        Optional<ReuniaoResponse> reuniao = reuniaoService.buscarReuniaoPorId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Reuniao encontrada com sucesso.");
        response.put("Body", reuniao.get());

        return ResponseEntity.ok(response);
    }

    /* ===================== UPDATE ===================== */

    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<Map<String, Object>> cancelarReuniao(
            @PathVariable Long id
    ) {
        Optional<ReuniaoResponse> reuniao = reuniaoService.cancelarReuniao(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Reuniao cancelada com sucesso.");
        response.put("Body", reuniao.get());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ping")
    public String teste(){
        return "pong";
    }

}