package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.EscritorioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EscritorioRepository extends JpaRepository<EscritorioModel, Long> {

    boolean existsByCnpj(String cnpj);

    Optional<EscritorioModel> findByCnpj(String cnpj);
}

