package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.MensagemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MensagensRepository extends JpaRepository<MensagemModel, Long> {
    Optional<List<MensagemModel>> findAllByChatModel_Id(Long chatId);

      /* =========================
        READ ultimas 30 mensagens
      ========================== */

    Page<MensagemModel> findByChatModel_Id(
            Long chatId,
            Pageable pageable
    );

    /* =========================
        Buscar ultima mensagem
    ========================== */

    Optional<MensagemModel> findFirstByChatModelIdOrderByCriadoEmDesc(Long chatId);

    List<MensagemModel> findAllByRemetente_Id(Long remetenteId);

    @Modifying
    @Query("DELETE FROM MensagemModel m WHERE m.remetente.id = :remetenteId")
    void deleteAllByRemetente_Id(@Param("remetenteId") Long remetenteId);
}
