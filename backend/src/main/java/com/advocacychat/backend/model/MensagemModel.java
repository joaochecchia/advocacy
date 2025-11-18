package com.advocacychat.backend.model;

import com.advocacychat.backend.enums.OrigemMensagem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensagemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chat_id", nullable = false)
    private ChatModel chatModel;

    @ManyToOne
    @JoinColumn(name = "remetente_id", nullable = false)
    private UsuarioModel remetente;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrigemMensagem origem;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm = LocalDateTime.now();
}
