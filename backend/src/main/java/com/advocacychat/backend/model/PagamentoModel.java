package com.advocacychat.backend.model;

import com.advocacychat.backend.enums.StatusPagamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel clienteModel;

    @ManyToOne
    @JoinColumn(name = "plano_id", nullable = false)
    private PlanoAssinaturaModel plano;

    @Column(nullable = false)
    private String gateway;

    @Column(name = "gateway_customer_id")
    private String gatewayCustomerId;

    @Column(name = "payment_method_token")
    private String paymentMethodToken;

    @Column(name = "referencia_gateway")
    private String referenciaGateway;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private StatusPagamento statusPagamento = StatusPagamento.PENDENTE;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "data_proxima_cobranca")
    private LocalDateTime dataProximaCobranca;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}