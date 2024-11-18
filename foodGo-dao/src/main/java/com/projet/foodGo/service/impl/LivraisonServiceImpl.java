package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.LivraisonDto;
import com.projet.foodGo.mapper.LivraisonConverter;
import com.projet.foodGo.model.Commande;
import com.projet.foodGo.model.Livraison;
import com.projet.foodGo.model.enumType.LivraisonState;
import com.projet.foodGo.repository.CommandeRepository;
import com.projet.foodGo.repository.LivraisonRepository;
import com.projet.foodGo.service.LivraisonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LivraisonServiceImpl implements LivraisonService {

    private final LivraisonRepository livraisonRepository;
    private final LivraisonConverter livraisonConverter;
    private final CommandeRepository commandeRepository;
    @Override
    public LivraisonDto createLivraison(LivraisonDto livraisonDto, UUID commandeId) {
        Optional<Commande> optionalCommande=commandeRepository.findByIdAndDeleteAtIsNull(commandeId);
        if(optionalCommande.isPresent()){
            Commande commande= optionalCommande.get();
            if(commande.isWithLivraison()){
                commande.setPrixTotal(commande.getPrixTotal()+ livraisonDto.getFraisLivraison());
                commandeRepository.save(commande);
                livraisonDto.setEtatLivraison(LivraisonState.PROPOSEE);
                Livraison livraison=livraisonConverter.toEntity(livraisonDto);
                livraison.setCommande(commande);
                return livraisonConverter.toDto(livraisonRepository.save(livraison));
            }
        }
        throw new IllegalArgumentException("Commande non trouvée");
    }


    @Override
    public LivraisonDto getLivraison(UUID id) {
        Optional<Livraison> optionalLivraison=livraisonRepository.findByIdAndDeleteAtIsNull(id);
        return optionalLivraison.map(livraisonConverter::toDto).orElse(null);
    }

    @Override
    public LivraisonDto getLivraisonByCommande(UUID commande_id) {
        Optional<Commande> optionalCommande=commandeRepository.findByIdAndDeleteAtIsNull(commande_id);
        if(optionalCommande.isPresent()){
            Optional<Livraison> optionalLivraison=livraisonRepository.findByCommandeAndDeleteAtIsNull(optionalCommande.get());
            return optionalLivraison.map(livraisonConverter::toDto).orElse(null);      }
        throw new IllegalArgumentException("Commande non trouvée");
    }

    @Override
    public LivraisonDto updateLivraison(UUID id, LivraisonDto livraisonDto) {
        Optional<Livraison> optionalLivraison=livraisonRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalLivraison.isPresent()){
            Livraison livraison= optionalLivraison.get();
            livraison.setTimeLivraison(livraisonDto.getTimeLivraison());
            livraison.setFraisLivraison(livraisonDto.getFraisLivraison());
            livraison.setNumeroLivreur(livraisonDto.getNumeroLivreur());
            return livraisonConverter.toDto(livraisonRepository.save(livraison));
        }
        return null;
    }


    @Override
    public LivraisonDto updateEtat(UUID id, LivraisonDto livraisonDto) {
        Optional<Livraison> optionalLivraison=livraisonRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalLivraison.isPresent()) {
            Livraison livraison = optionalLivraison.get();
            livraison.setEtatLivraison(livraisonDto.getEtatLivraison());
            return livraisonConverter.toDto(livraisonRepository.save(livraison));
        }
        return null;
    }


    @Override
    public boolean deleteLivraison(UUID id) {
        Optional<Livraison> optionalLivraison=livraisonRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalLivraison.isPresent()) {
            Livraison livraison = optionalLivraison.get();
            livraison.setDeleteAt(LocalDateTime.now());
            livraisonRepository.save(livraison);
            return true;
        }
        return false;
    }
}
