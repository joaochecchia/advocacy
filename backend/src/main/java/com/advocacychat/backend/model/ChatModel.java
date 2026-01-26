package com.advocacychat.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteModel clienteModel;

    @ManyToOne
    @JoinColumn(name = "advogado_id")
    private AdvogadoModel advogadoModel;

    @OneToMany(
            mappedBy = "chatModel",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MensagemModel> mensagens;

    private Boolean ativo = true;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}
