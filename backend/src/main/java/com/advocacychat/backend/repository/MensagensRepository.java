package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.MensagemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MensagensRepository extends JpaRepository<MensagemModel, Long> {
    Optional<List<MensagemModel>> findAllByChatModel_Id(Long chatId);
}
