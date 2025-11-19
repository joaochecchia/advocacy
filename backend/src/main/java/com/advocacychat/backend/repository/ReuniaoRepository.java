package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.ReuniaoModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReuniaoRepository extends JpaRepository<ReuniaoModel, Integer> {
}
