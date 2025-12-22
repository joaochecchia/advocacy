package com.advocacychat.backend.repository;

import com.advocacychat.backend.enums.StatusReuniao;
import com.advocacychat.backend.model.ReuniaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReuniaoRepository extends JpaRepository<ReuniaoModel, Long> {

    /**
     * Verifica se já existe reunião para o advogado no mesmo horário
     * ignorando reuniões canceladas.
     *
     * REGRA ATUAL:
     * - Não pode haver dois agendamentos no mesmo horário
     */
    boolean existsByAdvogadoModel_IdAndDataHoraAndStatusNot(
            Long advogadoId,
            LocalDateTime dataHora,
            StatusReuniao status
    );

    /**
     * Busca reuniões do advogado em um intervalo de tempo
     * (preparação para regra futura de sobreposição de horários).
     */
    List<ReuniaoModel> findByAdvogadoModel_IdAndDataHoraBetweenAndStatusNot(
            Long advogadoId,
            LocalDateTime inicio,
            LocalDateTime fim,
            StatusReuniao status
    );

    /**
     * Busca reunião pelo Google Event ID
     * (necessário para sincronização com Google Calendar).
     */
    Optional<ReuniaoModel> findByGoogleEventId(String googleEventId);
}