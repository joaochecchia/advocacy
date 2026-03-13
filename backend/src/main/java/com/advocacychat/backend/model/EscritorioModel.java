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
                @Index(name = "idx_escritorio_cnpj", columnList = "escritorio_cnpj")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EscritorioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "nome_escritório")
    String nomeEscritório;

    @Column(name = "nome_dono")
    String nomeDono;

    @Column(name = "escritorio_cnpj" ,nullable = false)
    String cnpj;

    @Column(name = "numero_advogados")
    Long numeroAdvogados;

    @OneToMany
    List<AdvogadoModel> advogadoModels;
}
