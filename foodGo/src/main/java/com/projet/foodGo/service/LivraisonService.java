package com.projet.foodGo.service;

import com.projet.foodGo.dto.LivraisonDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The interface Livraison service.
 */
@Service
public interface LivraisonService {
    /**
     * Create livraison livraison dto.
     *
     * @param livraisonDto the livraison dto
     * @param commandeId
     * @return the livraison dto
     */
    LivraisonDto createLivraison(LivraisonDto livraisonDto, UUID commandeId);

    /**
     * Gets livraison.
     *
     * @param id the id
     * @return the livraison
     */
    LivraisonDto getLivraison(UUID id);

    /**
     * Gets livraison by commande.
     *
     * @param commande_id the commande id
     * @return the livraison by commande
     */
    LivraisonDto getLivraisonByCommande(UUID commande_id);

    /**
     * Update livraison livraison dto.
     *
     * @param id           the id
     * @param livraisonDto the livraison dto
     * @return the livraison dto
     */
    LivraisonDto updateLivraison(UUID id,LivraisonDto livraisonDto);

    /**
     * Update etat livraison dto.
     *
     * @param id           the id
     * @param livraisonDto the livraison dto
     * @return the livraison dto
     */
    LivraisonDto updateEtat(UUID id,LivraisonDto livraisonDto);

    /**
     * Delete livraison boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteLivraison(UUID id);
}
