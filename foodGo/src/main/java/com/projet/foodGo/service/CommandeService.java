package com.projet.foodGo.service;

import com.projet.foodGo.dto.CommandeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CommandeService {
    CommandeDto createCommande(CommandeDto commandeDto, UUID user_id, UUID prestataireId);
    CommandeDto getCommande(UUID id);
    List<CommandeDto> getCommandes(UUID idPrestataire);
    List<CommandeDto> getCommandesByUser(UUID user_id);
    CommandeDto updateCommandDto(UUID commande_id,CommandeDto commandeDto);
    CommandeDto updateEtatCommande(UUID commande_id,CommandeDto commandeDto);
    CommandeDto updatePanier(UUID id,CommandeDto commandeDto);
    boolean deleteCommande(UUID id);
 }
