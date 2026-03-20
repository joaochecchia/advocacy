package com.advocacychat.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "advogados"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvogadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "escritorio_id")
    private EscritorioModel escritorio;

    private String oab;

    private String telefone;
}