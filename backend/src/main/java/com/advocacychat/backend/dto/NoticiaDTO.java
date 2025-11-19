package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticiaDTO {
    private Integer id;
    private String titulo;
    private String conteudo;
    private Integer autorId;
    private LocalDateTime publicadoEm;
    private Boolean ativo;
}
