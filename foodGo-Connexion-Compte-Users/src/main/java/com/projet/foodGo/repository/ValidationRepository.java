package com.projet.foodGo.repository;

import com.projet.foodGo.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {
    Optional<Validation> findByCode(String code);
    
    void deleteAllByExpirationBefore(Instant now);


    Optional<Validation> findByUtilisateur_EmailAndCode(String email, String code);
}
