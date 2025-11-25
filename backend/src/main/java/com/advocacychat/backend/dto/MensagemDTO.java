package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.OrigemMensagem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MensagemDTO {
    private Long id;
    private Long chatId;
    private Long remetenteId;
    private String conteudo;
    private OrigemMensagem origem;
    private LocalDateTime criadoEm;
}
