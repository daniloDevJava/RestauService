package com.projet.foodGo.repository;

import com.projet.foodGo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users,UUID> {
    Optional<Users> findByIdAndDeleteAtIsNull(UUID id);
    List<Users> findByDeleteAtIsNull();

}
