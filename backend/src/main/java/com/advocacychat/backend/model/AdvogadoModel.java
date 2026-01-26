package com.advocacychat.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "advogados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvogadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuarioModel;

    @Column(nullable = false, unique = true)
    private String oab;

    private String especialidade;

    @OneToMany(
            mappedBy = "advogadoModel",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ChatModel> chats;

    @OneToMany(
            mappedBy = "advogadoModel",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ReuniaoModel> reunioes;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}