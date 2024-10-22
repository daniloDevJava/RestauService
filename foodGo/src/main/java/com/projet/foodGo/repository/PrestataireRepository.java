package com.projet.foodGo.repository;

import com.projet.foodGo.model.Prestataire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrestataireRepository extends JpaRepository<Prestataire, UUID> {

    Optional<Prestataire> findByIdAndDeleteAtIsNull(UUID uuid);
    List<Prestataire> findByDeleteAtIsNull();
    Optional<Prestataire> findByNomAndDeleteAtIsNull(String nom);
}
