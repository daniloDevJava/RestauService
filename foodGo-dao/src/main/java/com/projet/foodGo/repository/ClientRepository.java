package com.projet.foodGo.repository;

import com.projet.foodGo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByIdAndDeleteAtIsNull(UUID uuid);
    List<Client> findByDeleteAtIsNull();
    Optional<Client> findByNomPrenom(String nomPremon);

}
