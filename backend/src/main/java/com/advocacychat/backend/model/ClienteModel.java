package com.advocacychat.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuarioModel;

    @OneToMany(mappedBy = "clienteModel", cascade = CascadeType.ALL)
    private List<ChatModel> chats;

    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    private String telefone;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}
