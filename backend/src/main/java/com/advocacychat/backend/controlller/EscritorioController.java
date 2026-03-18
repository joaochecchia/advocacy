package com.advocacychat.backend.controlller;

import com.advocacychat.backend.request.EscritorioRequest;
import com.advocacychat.backend.dto.EscritorioDTO;
import com.advocacychat.backend.response.EscritorioResponse;
import com.advocacychat.backend.service.EscritorioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/escritorio")
@RequiredArgsConstructor
public class EscritorioController {

    private final EscritorioService escritorioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, Object>> cadastrarEscritorio(
            @Valid @RequestBody EscritorioRequest request
    ) {
        EscritorioResponse escritorio = escritorioService.cadastrarEscritorio(request);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Escritório cadastrado com sucesso.");
        response.put("Body", escritorio);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getEscritorio/{id}")
    public ResponseEntity<Map<String, Object>> getEscritorioById(@PathVariable Long id) {
        Optional<EscritorioResponse> escritorio = escritorioService.buscarPorId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Escritório encontrado com sucesso.");
        response.put("Body", escritorio.get());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/patchEscritorio/{id}")
    public ResponseEntity<Map<String, Object>> patchEscritorio(
            @PathVariable Long id,
            @RequestBody EscritorioDTO request
    ) {
        Optional<EscritorioResponse> escritorio = escritorioService.editarEscritorio(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Escritório editado com sucesso.");
        response.put("Body", escritorio.get());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteEscritorio(@PathVariable Long id) {
        Long idDeletado = escritorioService.deletarEscritorio(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Escritório deletado com sucesso.");
        response.put("Body", "Id do escritório deletado: " + idDeletado);

        return ResponseEntity.ok(response);
    }
}

