package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.CommandeDto;
import com.projet.foodGo.model.Commande;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.repository.ProduitsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CommandeConverter {
    private final UserConverter userConverter;
    private final ProduitsRepository produitsRepository;



    public Commande toEntity(CommandeDto commandeDto){
        if(commandeDto==null)
            return null;
        List<Produits> listProduits=new ArrayList<>();
        Commande commande=new Commande();
        commande.setPrixTotal(commandeDto.getPrixTotal());
        commande.setPrixTotal(commandeDto.getPrixTotal());
        commande.setWithLivraison(commandeDto.isWithLivraison());
        commande.setUser(userConverter.toEntity(commandeDto.getUser()));


        //commande.setProduits(produitAlimentaireConverter.toEntityList(commandeDto.getProduits()));
        return commande;
    }

    public CommandeDto toDto(Commande commande){
        if(commande==null)
            return null;
        CommandeDto commandeDto=new CommandeDto();
        commandeDto.setId(commande.getId());
        commandeDto.setUser(userConverter.toDto(commande.getUser()));
        commandeDto.setIdPrestataire(commande.getIdPrestataire());
        //commandeDto.setProduits(produitAlimentaireConverter.toDtoList(commande.getProduits()));
        commandeDto.setWithLivraison(commande.isWithLivraison());
        return commandeDto;
    }
}
