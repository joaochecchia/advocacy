package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.MensagemModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MensagensRepository extends JpaRepository<MensagemModel, Long> {
    Optional<List<MensagemModel>> findAllByChat_Id(Long chatId);

      /* =========================
        READ ultimas 30 mensagens
      ========================== */

    Page<MensagemModel> findByChat_Id(
            Long chatId,
            Pageable pageable
    );

    /* =========================
        Buscar ultima mensagem
    ========================== */

    Optional<MensagemModel> findFirstByChat_IdOrderByIdDesc(Long chatId);

    List<MensagemModel> findAllByUsuario_Id(Long usuarioId);

    @Transactional
    @Modifying
    @Query("DELETE FROM MensagemModel m WHERE m.usuario.id = :usuarioId")
    void deleteAllByUsuario_Id(@Param("usuarioId") Long usuarioId);
}
