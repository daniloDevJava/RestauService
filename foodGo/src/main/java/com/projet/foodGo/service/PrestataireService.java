package com.projet.foodGo.service;

import com.projet.foodGo.dto.PrestataireDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PrestataireService {
    PrestataireDto createPrestataire(PrestataireDto prestataireDto);

    List<PrestataireDto> getPrestataires();

    PrestataireDto getPrestataire(UUID id);

    PrestataireDto getPrestataire(String nom);

    PrestataireDto updatePrestataire(UUID id, PrestataireDto prestataireDto);

    PrestataireDto updatePrestataireName(UUID id,PrestataireDto prestataireDto);

    PrestataireDto updatePrestataireMail(UUID id,PrestataireDto prestataireDto);

    PrestataireDto updatePrestataireCoordonnees(UUID id,PrestataireDto prestataireDto);

    boolean deletePrestataire(UUID id);
}