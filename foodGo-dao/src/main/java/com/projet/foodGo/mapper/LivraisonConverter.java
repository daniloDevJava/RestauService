package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.LivraisonDto;
import com.projet.foodGo.model.Livraison;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LivraisonConverter {
    private final CommandeConverter commandeConverter;

    public Livraison toEntity(LivraisonDto livraisonDto){
        if(livraisonDto==null)
            return null;
        Livraison livraison=new Livraison();
        livraison.setFraisLivraison(livraisonDto.getFraisLivraison());
        livraison.setTimeLivraison(livraisonDto.getTimeLivraison());
        livraison.setNumeroLivreur(livraisonDto.getNumeroLivreur());
        livraison.setEtatLivraison(livraisonDto.getEtatLivraison());
        return livraison;
    }

    public LivraisonDto toDto(Livraison livraison){
        if(livraison==null)
            return null;
        LivraisonDto livraisonDto=new LivraisonDto();
        livraisonDto.setId(livraison.getId());
        livraisonDto.setEtatLivraison(livraison.getEtatLivraison());
        livraisonDto.setCommandeId(livraison.getCommande().getId());
        livraisonDto.setNumeroLivreur(livraison.getNumeroLivreur());
        livraisonDto.setTimeLivraison(livraison.getTimeLivraison());
        livraisonDto.setFraisLivraison(livraison.getFraisLivraison());
        return livraisonDto;
    }
}
