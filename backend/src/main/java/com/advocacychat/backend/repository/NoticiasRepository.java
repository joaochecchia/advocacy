package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.NoticiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiasRepository extends JpaRepository<NoticiaModel, Integer> {
}
