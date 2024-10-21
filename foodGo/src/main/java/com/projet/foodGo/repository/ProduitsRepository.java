package com.projet.foodGo.repository;

import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProduitsRepository extends JpaRepository<Produits, UUID> {

    Optional<Produits> findByIdAndDeleteAtIsNull(UUID uuid);
    List<Produits> findByDeleteAtIsNull();
    List<Produits> findByPrestataireOrderByDeleteAtIsNull(Prestataire prestataire);
    List<Produits> findByPrestataireOrderByDeleteAtIsNotNull(Prestataire prestataire);
    List<Produits> findByLibelleAndDeleteAtIsNull(String libelle);
}
