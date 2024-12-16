package com.projet.foodGo.repository;

import com.projet.foodGo.model.Jwt;
import com.projet.foodGo.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Jwt, Long> {
    Optional<Jwt> findByRefreshToken(RefreshToken refreshToken);
}
