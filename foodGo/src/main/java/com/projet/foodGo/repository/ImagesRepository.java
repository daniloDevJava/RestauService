package com.projet.foodGo.repository;


import com.projet.foodGo.model.Images;
import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.model.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImagesRepository extends JpaRepository<Images,Long> {

    Optional<Images> findByIdAndDeleteAtIsNull(Long id);
    List<Images> findByDeleteAtIsNull();
    List<Images> findByProduitAndDeleteAtIsNull(Produits produits);
}
