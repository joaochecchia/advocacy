package com.advocacychat.backend.repository;

import com.advocacychat.backend.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminModel, Long> {
}
