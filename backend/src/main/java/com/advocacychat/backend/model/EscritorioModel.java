package com.advocacychat.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "escritorio",
        indexes = {
                @Index(name = "idx_escritorio_cnpj", columnList = "cnpj_escritorio"),
                @Index(name = "idx_escritorio_nome", columnList = "nome_escritorio")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EscritorioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nome_escritorio")
    String nomeEscritorio;

    @Column(name = "nome_dono")
    String nomeDono;

    @Column(name = "cnpj_escritorio" ,nullable = false)
    String cnpj;

    @Column(name = "numero_advogados")
    Long numeroAdvogados;

    @CreationTimestamp
    @Column(name = "data_craicao", updatable = false)
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    @Column(name = "data_criacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany
    List<AdvogadoModel> advogadoModels;
}
