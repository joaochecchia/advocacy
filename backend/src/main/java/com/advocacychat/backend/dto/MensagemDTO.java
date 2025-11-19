package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.OrigemMensagem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MensagemDTO {
    private Integer id;
    private Integer chatId;
    private Integer remetenteId;
    private String conteudo;
    private OrigemMensagem origem;
    private LocalDateTime criadoEm;
}
