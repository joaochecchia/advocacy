    package com.advocacychat.backend.model;

    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDateTime;
    import java.util.List;

    @Entity
    @Table(name = "clientes")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ClienteModel {

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
        private String cpf;

        @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
        @JsonManagedReference
        private List<ChatModel> chats;
    }