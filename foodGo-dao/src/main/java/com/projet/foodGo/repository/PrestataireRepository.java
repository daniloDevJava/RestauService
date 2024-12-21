package com.projet.foodGo.repository;


import com.projet.foodGo.model.Prestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrestataireRepository extends JpaRepository<Prestataire, UUID> {

    Optional<Prestataire> findByIdAndDeleteAtIsNull(UUID uuid);
    List<Prestataire> findByDeleteAtIsNull();
    Optional<Prestataire> findByNomAndDeleteAtIsNull(String nom);
    @Query("SELECT p FROM Prestataire p JOIN p.listProduits pr WHERE pr.libelle = :libelle")
    List<Prestataire> findPrestatairesByProduitLibelle(@Param("libelle") String libelle);
    Optional<Prestataire> findByAdresseMailAndDeleteAtIsNull(String AdresseMail);

}
