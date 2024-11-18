package com.projet.foodGo.repository;

import com.projet.foodGo.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

    Optional<Admin> findByIdAndDeleteAtIsNull(UUID uuid);
    List<Admin> findByDeleteAtIsNull();
    Optional<Admin> findByNomAndDeleteAtIsNull(String nom);
}
