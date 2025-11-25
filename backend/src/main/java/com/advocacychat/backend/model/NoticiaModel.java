package com.advocacychat.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "noticias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private UsuarioModel autor;

    @Column(name = "publicado_em")
    private LocalDateTime publicadoEm = LocalDateTime.now();

    private Boolean ativo = true;
}