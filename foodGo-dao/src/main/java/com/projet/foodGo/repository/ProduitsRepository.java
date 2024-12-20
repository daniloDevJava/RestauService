package com.projet.foodGo.repository;

import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProduitsRepository extends JpaRepository<Produits, UUID> {

    Optional<Produits> findByIdAndDeleteAtIsNull(UUID uuid);
    List<Produits> findByDeleteAtIsNull();
    List<Produits> findByPrestataireAndDeleteAtIsNull(Prestataire prestataire);
    @Query("SELECT p FROM produits p WHERE p.prestataire.id = :prestataire_id AND p.deleteAt IS NULL AND p.libelle = :libelle ")
    Optional<Produits> findByPrestataireOrderByLibelle(@Param("prestataire_id") UUID prestataire_id,@Param("libelle") String  libelle);
    List<Produits> findByPrestataireAndDeleteAtIsNotNull(Prestataire prestataire);
    @Query("UPDATE  produits SET quantiteStock = :quantite WHERE id = :id")
    void updateQuantiteStock(@Param("quantite") int quantite,@Param("id") UUID id);
    List<Produits> findByLibelleAndDeleteAtIsNull(String libelle);
}
