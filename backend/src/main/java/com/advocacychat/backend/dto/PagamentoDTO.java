package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.MetodoPagamento;
import com.advocacychat.backend.enums.StatusPagamento;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagamentoDTO {
    private Long id;
    private Long assinaturaId;
    private String pagarmeTransaction;
    private BigDecimal valor;
    private StatusPagamento status;
    private MetodoPagamento metodo;
    private LocalDateTime paidAt;
}
