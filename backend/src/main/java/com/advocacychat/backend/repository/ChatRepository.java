package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.ChatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatModel, Integer> {
}
