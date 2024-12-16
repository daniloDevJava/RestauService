package com.projet.foodGo.repository;

import com.projet.foodGo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {
    Optional<Utilisateur> findByEmailAndDeleteAtIsNull(String email);
}
