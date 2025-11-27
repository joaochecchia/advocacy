package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.AdvogadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvogadoRepository extends JpaRepository<AdvogadoModel, Long> {
    Optional<AdvogadoModel> findByUsuarioModel_Id(Long usuarioId);
}
