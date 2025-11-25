package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticiaDTO {
    private Long id;
    private String titulo;
    private String conteudo;
    private Long autorId;
    private LocalDateTime publicadoEm;
    private Boolean ativo;
}
