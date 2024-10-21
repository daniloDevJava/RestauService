package com.projet.foodGo.service;

import com.projet.foodGo.dto.LivraisonDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface LivraisonService {
    LivraisonDto createLivraison(LivraisonDto livraisonDto);
    LivraisonDto getLivraison(UUID id);
    LivraisonDto getLivraisonByCommande(UUID commande_id);
    LivraisonDto updateLivraison(UUID id,LivraisonDto livraisonDto);
    LivraisonDto updateEtat(UUID id,LivraisonDto livraisonDto);
    boolean deleteLivraison(UUID id);
}
