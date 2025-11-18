package com.advocacychat.backend.model;

import com.advocacychat.backend.enums.StatusReuniao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reunioes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReuniaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel clienteModel;

    @ManyToOne
    @JoinColumn(name = "advogado_id", nullable = false)
    private AdvogadoModel advogadoModel;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusReuniao status = StatusReuniao.PENDENTE;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}