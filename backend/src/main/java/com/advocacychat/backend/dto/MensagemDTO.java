package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.TipoMensagem;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MensagemDTO {
    private Long id;
    private Long chatId;
    private Long usuarioId;
    private TipoMensagem tipo;
    private String conteudo;
    private LocalDateTime dataCriacao;
}
