package com.projet.foodGo.repository;

import com.projet.foodGo.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommandeRepository extends JpaRepository<Commande, UUID> {

    Optional<Commande> findByIdAndDeleteAtIsNull(UUID uuid);
    List<Commande> findByDeleteAtIsNull();

}
