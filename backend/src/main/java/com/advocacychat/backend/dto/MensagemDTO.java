package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.TipoMensagem;
import lombok.Data;

@Data
public class MensagemDTO {
    private Long id;
    private Long chatId;
    private Long usuarioId;
    private TipoMensagem tipo;
    private String conteudo;
}
