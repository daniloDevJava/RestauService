package com.projet.foodGo.service;

import com.projet.foodGo.dto.CommandeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Commande service.
 */
@Service
public interface CommandeService {
    /**
     * Create commande commande dto.
     *
     * @param commandeDto   the commande dto
     * @param user_id       the user id
     * @param prestataireId the prestataire id
     * @return the commande dto
     */
    CommandeDto createCommande(CommandeDto commandeDto, UUID user_id, UUID prestataireId);

    /**
     * Gets commande.
     *
     * @param id the id
     * @return the commande
     */
    CommandeDto getCommande(UUID id);

    /**
     * Gets commandes.
     *
     * @param idPrestataire the id prestataire
     * @return the commandes
     */
    List<CommandeDto> getCommandes(UUID idPrestataire);

    /**
     * Gets commandes by user.
     *
     * @param user_id the user id
     * @return the commandes by user
     */
    List<CommandeDto> getCommandesByUser(UUID user_id);

    /**
     * Update etat commande commande dto.
     *
     * @param commande_id the commande id
     * @param commandeDto the commande dto
     * @return the commande dto
     */
    CommandeDto updateEtatCommande(UUID commande_id,CommandeDto commandeDto);

    /**
     * Update panier commande dto.
     *
     * @param id          the id
     * @param commandeDto the commande dto
     * @return the commande dto
     */
    CommandeDto updatePanier(UUID id,CommandeDto commandeDto);

    /**
     * Delete commande boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteCommande(UUID id);
 }
