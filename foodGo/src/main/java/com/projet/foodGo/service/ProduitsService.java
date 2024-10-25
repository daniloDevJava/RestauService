package com.projet.foodGo.service;

import com.projet.foodGo.dto.ProduitsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProduitsService {
    ProduitsDto createProduct(ProduitsDto produitAlimentaireDto, UUID prestataire_id);
    List<ProduitsDto> getProducts(UUID prestataire_id);
    List<ProduitsDto> getProducts();
    ProduitsDto getProduct(UUID id);
    ProduitsDto getProduct(String mom, UUID prestataire_id);
    ProduitsDto updateProduct(UUID id, ProduitsDto produitAlimentaireDto);
    List<ProduitsDto> getCorbeille(UUID prestataire_id);
    ProduitsDto updateQuantiteStock(UUID produit_id,ProduitsDto produitsDto);
    boolean deleteProduct(UUID id);
}
