package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.ChatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatModel, Long> {
    boolean existsByIdAndClienteModel_UsuarioModel_Id(Long chatId, Long usuarioId);

    List<ChatModel> findAllByClienteModel_Id(Long id);
}
