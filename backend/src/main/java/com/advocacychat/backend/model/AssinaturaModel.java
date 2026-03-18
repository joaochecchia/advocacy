package com.advocacychat.backend.model;

import com.advocacychat.backend.enums.StatusAssinatura;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "assinaturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinaturaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModel cliente;

    private String pagarmeCustomerId;
    private String pagarmeSubscription;

    private String plano;

    @Enumerated(EnumType.STRING)
    private StatusAssinatura status;

    private LocalDate proximoCobranca;

    @OneToMany(mappedBy = "assinatura", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<PagamentoModel> pagamentos;
}
