package com.advocacychat.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany
    List<AdvogadoModel> advogadoModels;
}
