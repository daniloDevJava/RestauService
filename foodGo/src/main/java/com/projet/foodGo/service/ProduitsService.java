package com.projet.foodGo.service;

import com.projet.foodGo.dto.ProduitsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface ProduitsService {
    ProduitsDto createProduct(ProduitsDto produitAlimentaireDto);
    List<ProduitsDto> getProducts(UUID prestataire_id);
    List<ProduitsDto> getProducts();
    ProduitsDto getProduct(UUID id);
    ProduitsDto getProduct(String mom);
    ProduitsDto updateProduct(UUID id, ProduitsDto produitAlimentaireDto);
    boolean deleteProduct(UUID id);
}
