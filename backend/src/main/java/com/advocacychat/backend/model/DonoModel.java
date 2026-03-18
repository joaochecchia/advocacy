package com.advocacychat.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "donos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "escritorio_id")
    private EscritorioModel escritorio;

    private String nome;
    private String telefone;
}
