package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.CommandeDto;
import com.projet.foodGo.model.Commande;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.model.Users;
import com.projet.foodGo.repository.ProduitsRepository;
import com.projet.foodGo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CommandeConverter {
    private final UserRepository userRepository;
    private final ProduitsRepository produitsRepository;

    public Commande toEntity(CommandeDto commandeDto){
        if(commandeDto==null)
            return null;
        Optional<Users> optionaluser=userRepository.findByIdAndDeleteAtIsNull(commandeDto.getIdUser());
        List<Produits> listProduits=new ArrayList<>();
        Commande commande=new Commande();
        commande.setPrixTotal(commandeDto.getPrixTotal());
        commande.setWithLivraison(commandeDto.isWithLivraison());
        commande.setIdPrestataire(commandeDto.getIdPrestataire());
        if(optionaluser.isPresent())
        {
            Users user=optionaluser.get();
            commande.setUser(user);
        }
        for(UUID id: commandeDto.getProduits()){
            Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(id);
            optionalProduits.ifPresent(listProduits::add);
        }
        commande.setProduits(listProduits);
        return commande;
    }

    public CommandeDto toDto(Commande commande){
        if(commande==null)
            return null;
        List<UUID> listProduits=new ArrayList<>();
        CommandeDto commandeDto=new CommandeDto();
        commandeDto.setId(commande.getId());
        commandeDto.setIdUser(commande.getUser().getId());
        commandeDto.setIdPrestataire(commande.getIdPrestataire());
        for(Produits produits: commande.getProduits()){
            if(produits.getDeleteAt()!=null)
                listProduits.add(produits.getId());
        }
        commandeDto.setProduits(listProduits);
        commandeDto.setWithLivraison(commande.isWithLivraison());
        return commandeDto;
    }
}
