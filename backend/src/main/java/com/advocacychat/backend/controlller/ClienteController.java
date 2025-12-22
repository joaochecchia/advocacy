package com.advocacychat.backend.controlller;

import com.advocacychat.backend.dto.ClienteDTO;
import com.advocacychat.backend.response.ClienteResponse;
/*
import com.advocacychat.backend.service.ChatService;
*/
import com.advocacychat.backend.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /*
    private final ChatService chatService;
    */

    @GetMapping("/getClienteByUserId/{id}")
    public ResponseEntity<Map<String, Object>> getClienteByUserId(@PathVariable Long id){
        Optional<ClienteResponse> cliente = clienteService.findClienteByUserId(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Cliente encontrado com sucesso.");
        response.put("Body", cliente.get());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCliente/{id}")
    public ResponseEntity<Map<String, Object>> getClienteById(@PathVariable Long id){
        Optional<ClienteResponse> cliente = clienteService.findClienteById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Cliente encontrado com sucesso.");
        response.put("Body", cliente.get());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/patchCliente/{id}")
    public ResponseEntity<Map<String, Object>> patchClienteByUserId(@PathVariable Long id, @RequestBody ClienteDTO request){
        Optional<ClienteResponse> cliente = clienteService.patchClienteById(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Cliente editado com sucesso.");
        response.put("Body", cliente.get());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteClienteById(@PathVariable Long id){
        Long idDeletado = clienteService.deleteClienteById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Cliente editado com sucesso.");
        response.put("Body", "Id do usuario deletado: " + idDeletado);

        return ResponseEntity.ok(response);
    }
}