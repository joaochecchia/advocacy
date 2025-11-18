package com.advocacychat.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "advogados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvogadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuarioModel;

    @Column(nullable = false, unique = true)
    private String oab;

    private String especialidade;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}