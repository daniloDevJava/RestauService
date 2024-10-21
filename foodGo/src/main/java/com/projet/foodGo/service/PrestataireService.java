package com.projet.foodGo.service;

import com.projet.foodGo.dto.PrestataireDto;
import org.springframework.stereotype.Service;

@Service
public interface PrestataireService {
    PrestataireDto createPrestataire(PrestataireDto prestataireDto);

}
