package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.LivraisonDto;
import com.projet.foodGo.model.Livraison;
import org.springframework.stereotype.Component;

@Component
public class LivraisonConverter {
    private final CommandeConverter commandeConverter;

    public LivraisonConverter(CommandeConverter commandeConverter) {
        this.commandeConverter = commandeConverter;
    }

    public Livraison toEntity(LivraisonDto livraisonDto){
        if(livraisonDto==null)
            return null;
        Livraison livraison=new Livraison();
        livraison.setFraisLivraison(livraisonDto.getFraisLivraison());
        livraison.setTimeLivraison(livraisonDto.getTimeLivraison());
        livraison.setNumeroLivreur(livraisonDto.getNumeroLivreur());
        livraison.setEtatLivraison(livraisonDto.getEtatLivraison());
        livraison.setCommande(commandeConverter.toEntity(livraisonDto.getCommandeDto()));
        return livraison;
    }

    public LivraisonDto toDto(Livraison livraison){
        if(livraison==null)
            return null;
        LivraisonDto livraisonDto=new LivraisonDto();
        livraisonDto.setId(livraison.getId());
        livraisonDto.setNumeroLivreur(livraison.getNumeroLivreur());
        livraisonDto.setTimeLivraison(livraison.getTimeLivraison());
        livraisonDto.setFraisLivraison(livraison.getFraisLivraison());
        livraisonDto.setCommandeDto(commandeConverter.toDto(livraison.getCommande()));
        return livraisonDto;
    }
}
