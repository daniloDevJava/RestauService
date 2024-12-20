package com.projet.foodGo.service;

import com.projet.foodGo.dto.PrestataireDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Prestataire service.
 */
@Service
public interface PrestataireService {
    /**
     * Create prestataire prestataire dto.
     *
     * @param prestataireDto the prestataire dto
     * @return the prestataire dto
     */
    PrestataireDto createPrestataire(PrestataireDto prestataireDto);

    /**
     * Gets prestataires.
     *
     * @return the prestataires
     */
    List<PrestataireDto> getPrestataires();

    /**
     * Gets prestataire.
     *
     * @param id the id
     * @return the prestataire
     */
    PrestataireDto getPrestataire(UUID id);

    /**
     * Gets prestataire.
     *
     * @param nom the nom
     * @return the prestataire
     */
    PrestataireDto getPrestataire(String nom);

    /**
     * Update prestataire prestataire dto.
     *
     * @param id             the id
     * @param prestataireDto the prestataire dto
     * @return the prestataire dto
     */
    PrestataireDto updatePrestataire(UUID id, PrestataireDto prestataireDto);

    /**
     * Update prestataire name prestataire dto.
     *
     * @param id             the id
     * @param prestataireDto the prestataire dto
     * @return the prestataire dto
     */
    PrestataireDto updatePrestataireName(UUID id,PrestataireDto prestataireDto);

    /**
     * Update prestataire mail prestataire dto.
     *
     * @param id             the id
     * @param prestataireDto the prestataire dto
     * @return the prestataire dto
     */
    PrestataireDto updatePrestataireMail(UUID id,PrestataireDto prestataireDto);

    /**
     * Update prestataire coordonnees prestataire dto.
     *
     * @param id             the id
     * @param prestataireDto the prestataire dto
     * @return the prestataire dto
     */
    PrestataireDto updatePrestataireCoordonnees(UUID id,PrestataireDto prestataireDto);

    /**
     * Delete prestataire boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deletePrestataire(UUID id);

    PrestataireDto updateAdresse(UUID prestataireId,PrestataireDto prestataireDto);

    List<PrestataireDto> getPrestatairesByFood(String libelle);
}