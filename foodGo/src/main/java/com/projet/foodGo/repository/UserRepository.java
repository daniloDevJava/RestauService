package com.projet.foodGo.repository;

import com.projet.foodGo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UUID, User> {
    Optional<User> findByIdAndDeleteAtIsNull(UUID id);
    Optional<User> findByDeleteAtIsNull();

}
