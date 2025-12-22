package com.advocacychat.backend.model;

import com.advocacychat.backend.enums.StatusReuniao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "reunioes",
        indexes = {
                @Index(name = "idx_reuniao_advogado_data", columnList = "advogado_id, data_hora"),
                @Index(name = "idx_reuniao_cliente_data", columnList = "cliente_id, data_hora"),
                @Index(name = "idx_reuniao_status", columnList = "status")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReuniaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* ===================== RELACIONAMENTOS ===================== */

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel clienteModel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "advogado_id", nullable = false)
    private AdvogadoModel advogadoModel;

    /* ===================== DADOS DA REUNIÃO ===================== */

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private StatusReuniao status = StatusReuniao.PENDENTE;

    /* ===================== GOOGLE INTEGRATION ===================== */

    @Column(name = "google_event_id", unique = true)
    private String googleEventId;

    @Column(name = "google_meet_link")
    private String googleMeetLink;

    /* ===================== AUDITORIA ===================== */

    @Column(name = "criado_em", nullable = false, updatable = false)
    private final LocalDateTime criadoEm = LocalDateTime.now();
}