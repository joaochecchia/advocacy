package com.advocacychat.backend.controlller;

import com.advocacychat.backend.dto.MensagemDTO;
import com.advocacychat.backend.service.MensagensService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
