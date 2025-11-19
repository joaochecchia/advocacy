package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.MensagemModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagensRepository extends JpaRepository<MensagemModel, Integer> {
}
