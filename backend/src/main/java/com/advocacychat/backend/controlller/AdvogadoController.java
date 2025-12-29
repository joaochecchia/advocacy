package com.advocacychat.backend.controlller;

import com.advocacychat.backend.dto.AdvogadoDTO;
import com.advocacychat.backend.response.AdvogadoResponse;
import com.advocacychat.backend.service.AdvogadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/advogado")
@RequiredArgsConstructor
public class AdvogadoController {

    private final AdvogadoService advogadoService;

    @GetMapping("/getAdvogadoByUserId/{id}")
    public ResponseEntity<Map<String, Object>> getAvogadoByUserId(@PathVariable Long id){
        Optional<AdvogadoResponse> advogado = advogadoService.findAdvogadoByUserId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Advogado encontrado com sucesso.");
        response.put("Body", advogado.get());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAdvogado/{id}")
    public ResponseEntity<Map<String, Object>> getAdbogadoById(@PathVariable Long id){
        Optional<AdvogadoResponse> advogado = advogadoService.findAdvogadoById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Advogado encontrado com sucesso.");
        response.put("Body", advogado.get());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/patchAdvogado/{id}")
    public ResponseEntity<Map<String, Object>> patchAdvogadoByUserId(
            @PathVariable Long id,
            @RequestBody AdvogadoDTO request
    ){
        Optional<AdvogadoResponse> advogado = advogadoService.patchAdvogadoById(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Advogado editado com sucesso.");
        response.put("Body", advogado.get());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteAdogadoById(@PathVariable Long id){
        Long idDeletado = advogadoService.deleteAdvogadoById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Advogado editado com sucesso.");
        response.put("Body", "Id do usuario deletado: " + idDeletado);

        return ResponseEntity.ok(response);
    }
}
