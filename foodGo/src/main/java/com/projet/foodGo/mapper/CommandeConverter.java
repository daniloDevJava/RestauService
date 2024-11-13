package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.CommandeDto;
import com.projet.foodGo.model.Commande;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.model.Users;
import com.projet.foodGo.repository.ProduitsRepository;
import com.projet.foodGo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class CommandeConverter {
    private final UserRepository userRepository;
    private final ProduitsRepository produitsRepository;
    private final UserConverter userConverter;

    public Commande toEntity(CommandeDto commandeDto){
        if(commandeDto==null)
            return null;
        Optional<Users> optionaluser=userRepository.findByIdAndDeleteAtIsNull(commandeDto.getIdUser());
        List<Produits> listProduits=new ArrayList<>();
        List<UUID> uuidList=new ArrayList<>(commandeDto.getProductQuantite().keySet());
        Commande commande=new Commande();
        commande.setEtat(commandeDto.getEtat());
        commande.setPrixTotal(commandeDto.getPrixTotal());
        commande.setWithLivraison(commandeDto.isWithLivraison());
        commande.setIdPrestataire(commandeDto.getIdPrestataire());
        List<Integer> integerList = new ArrayList<>(commandeDto.getProductQuantite().values());
        commande.setListQuantiteesCommandees(integerList);
        if(optionaluser.isPresent())
        {
            Users user=optionaluser.get();
            commande.setUser(user);
        }
        for(UUID id:uuidList){
            Optional<Produits> produits=produitsRepository.findByIdAndDeleteAtIsNull(id);
            produits.ifPresent(listProduits::add);
        }


        commande.setProduits(listProduits);
        return commande;
    }

    public CommandeDto toDto(Commande commande){
        if(commande==null)
            return null;
        Optional<Users> optionaluser=userRepository.findByIdAndDeleteAtIsNull(commande.getUser().getId());
        Map<UUID , Integer> hmp=new HashMap<>();
        CommandeDto commandeDto=new CommandeDto();
        commandeDto.setId(commande.getId());
        commandeDto.setIdUser(commande.getUser().getId());
        if(optionaluser.isPresent())
        {
            Users user=optionaluser.get();
            commandeDto.setUserDto(userConverter.toDto(user));
        }
        commandeDto.setIdPrestataire(commande.getIdPrestataire());
        for(int i=0;i<commande.getProduits().size();i++){
            hmp.put(commande.getProduits().get(i).getId(),commande.getListQuantiteesCommandees().get(i));
        }
        commandeDto.setProductQuantite(hmp);
        commandeDto.setPrixTotal(commande.getPrixTotal());
        commandeDto.setEtat(commande.getEtat());
        commandeDto.setWithLivraison(commande.isWithLivraison());
        return commandeDto;
    }
}
