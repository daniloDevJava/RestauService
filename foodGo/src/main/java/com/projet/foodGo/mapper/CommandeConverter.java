package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.CommandeDto;
import com.projet.foodGo.model.Commande;
import org.springframework.stereotype.Component;

@Component
public class CommandeConverter {
    private final UserConverter userConverter;
    private final ProduitAlimentaireConverter produitAlimentaireConverter;

    public CommandeConverter(UserConverter userConverter, ProduitAlimentaireConverter produitAlimentaireConverter) {
        this.userConverter = userConverter;
        this.produitAlimentaireConverter = produitAlimentaireConverter;
    }

    public Commande toEntity(CommandeDto commandeDto){
        if(commandeDto==null)
            return null;
        Commande commande=new Commande();
        commande.setPrixTotal(commandeDto.getPrixTotal());
        commande.setPrixTotal(commandeDto.getPrixTotal());
        commande.setWithLivraison(commandeDto.isWithLivraison());
        commande.setUser(userConverter.toEntity(commandeDto.getUser()));
        commande.setProduits(produitAlimentaireConverter.toEntityList(commandeDto.getProduits()));
        return commande;
    }

    public CommandeDto toDto(Commande commande){
        if(commande==null)
            return null;
        CommandeDto commandeDto=new CommandeDto();
        commandeDto.setId(commande.getId());
        commandeDto.setUser(userConverter.toDto(commande.getUser()));
        commandeDto.setIdPrestataire(commande.getIdPrestataire());
        commandeDto.setProduits(produitAlimentaireConverter.toDtoList(commande.getProduits()));
        commandeDto.setWithLivraison(commande.isWithLivraison());
        return commandeDto;
    }
}
