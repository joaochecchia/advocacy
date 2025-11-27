package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    Optional<ClienteModel> findByUsuarioModel_Id(Long usuarioId);
}
