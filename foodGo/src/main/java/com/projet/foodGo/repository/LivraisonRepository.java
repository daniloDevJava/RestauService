package com.projet.foodGo.repository;

import com.projet.foodGo.model.Commande;
import com.projet.foodGo.model.Livraison;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivraisonRepository extends JpaRepository<Livraison, UUID> {
    Optional<Livraison> findByIdAndDeleteAtIsNull(UUID id);
    List<Livraison> findByDeleteAtIsNull();
    Optional<Livraison> findByCommandeAndDeleteAtIsNull(Commande commande);
}
