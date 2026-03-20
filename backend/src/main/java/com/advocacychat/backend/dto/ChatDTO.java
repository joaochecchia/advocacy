package com.advocacychat.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDTO {
    private Long id;
    private Long clienteId;
    private LocalDateTime dataCriacao;
    private Long escritorioId;
}
