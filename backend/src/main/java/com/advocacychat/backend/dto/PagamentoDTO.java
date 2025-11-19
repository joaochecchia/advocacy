package com.advocacychat.backend.dto;

import com.advocacychat.backend.enums.StatusPagamento;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PagamentoDTO {
    private Integer id;
    private Integer clienteId;
    private Integer planoId;
    private String gateway;
    private String gatewayCustomerId;
    private String paymentMethodToken;
    private String referenciaGateway;
    private StatusPagamento statusPagamento;
    private LocalDateTime dataPagamento;
    private LocalDateTime dataProximaCobranca;
    private Double valor;
    private LocalDateTime criadoEm;
}
