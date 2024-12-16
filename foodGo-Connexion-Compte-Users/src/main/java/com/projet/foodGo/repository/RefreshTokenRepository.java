package com.projet.foodGo.repository;

import com.projet.foodGo.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByValeurAndExpireFalse(String valeur);
    Optional<RefreshToken> findByValeur(String valeur);
}

