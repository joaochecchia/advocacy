package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.PagamentoModel;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentosRepository extends JpaRepository<PagamentoModel, Integer> {
}
