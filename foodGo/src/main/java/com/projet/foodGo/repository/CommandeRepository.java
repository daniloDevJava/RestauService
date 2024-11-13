package com.projet.foodGo.repository;

import com.projet.foodGo.model.Commande;
import com.projet.foodGo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommandeRepository extends JpaRepository<Commande, UUID> {

    Optional<Commande> findByIdAndDeleteAtIsNull(UUID uuid);
    List<Commande> findByDeleteAtIsNull();

    @Query("select c from Commande c where c.idPrestataire = ?1 and c.deleteAt is not null")
    List<Commande> findByIdPrestataireAndDeleteAtNotNull(UUID idPrestataire);
    @Query("select c from Commande c where c.idPrestataire = ?1 and c.deleteAt is null")
    List<Commande> findByIdPrestataireAndDeleteAtIsNull(UUID idPrestataire);

    List<Commande> findByUserAndDeleteAtNull(Users user);
}
