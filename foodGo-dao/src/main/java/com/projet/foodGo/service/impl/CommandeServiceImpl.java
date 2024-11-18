package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.CommandeDto;
import com.projet.foodGo.exeptions.QuantitiesOutOfBoundExceptions;
import com.projet.foodGo.mapper.CommandeConverter;
import com.projet.foodGo.model.Commande;
import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.model.Users;
import com.projet.foodGo.model.enumType.Etat;
import com.projet.foodGo.repository.CommandeRepository;
import com.projet.foodGo.repository.PrestataireRepository;
import com.projet.foodGo.repository.ProduitsRepository;
import com.projet.foodGo.repository.UserRepository;
import com.projet.foodGo.service.CommandeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Commande service.
 */
@Service
@AllArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private final PrestataireRepository prestataireRepository;
    private final CommandeRepository commandeRepository;
    private final CommandeConverter commandeConverter;
    private final ProduitsRepository produitsRepository;
    private final UserRepository userRepository;

    @Override
    public CommandeDto createCommande(CommandeDto commandeDto, UUID user_id, UUID prestataireId) {
        try{
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(user_id);
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(prestataireId);
        double som=0.0;

        if(optionalPrestataire.isPresent() && optionalUsers.isPresent())
        {
            commandeDto.setIdUser(user_id);
            commandeDto.setIdPrestataire(prestataireId);
            Commande commande= commandeConverter.toEntity(commandeDto);

            for(Map.Entry<UUID ,Integer> entry:commandeDto.getProductQuantite().entrySet()){
                Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(entry.getKey());
                if(optionalProduits.isPresent()) {
                    Produits produits= optionalProduits.get();
                    int resteEnStock = optionalProduits.get().getQuantiteStock();
                    if (resteEnStock - entry.getValue() >= 0){
                        som += entry.getValue() * optionalProduits.get().getPrixUnitaire();
                        resteEnStock=resteEnStock - entry.getValue();
                        produits.setQuantiteStock(resteEnStock);
                        produitsRepository.save(produits);
                    }
                    else
                        throw new QuantitiesOutOfBoundExceptions("Quantité en stock pour un produit spécifié dans la commande non suffisante");
                }
                else
                    throw new IllegalArgumentException("Un Produit n'a pas été trouvé");
            }
            commande.setEtat(Etat.EN_COURS);
            commande.setPrixTotal(som);
            return commandeConverter.toDto(commandeRepository.save(commande));


        }
        else
            throw new EntityNotFoundException("Restaurant ou client non trouvé");
        }
        catch (IndexOutOfBoundsException e){
            System.err.println(e.getMessage());
            throw new QuantitiesOutOfBoundExceptions("Le nombre de produits doit etre egal au nombre de qunatités demandées");

        }

    }

    @Override
    public CommandeDto getCommande(UUID id) {
        Optional<Commande> optionalCommande=commandeRepository.findByIdAndDeleteAtIsNull(id);
        return optionalCommande.map(commandeConverter::toDto).orElse(null);
    }

    @Override
    public List<CommandeDto> getCommandes(UUID idPrestataire) {
        List<Commande> commandeList= commandeRepository.findByIdPrestataireAndDeleteAtIsNull(idPrestataire);
        return commandeList.stream()
                .map(commandeConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommandeDto> getCommandesByUser(UUID user_id) {
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(user_id);
        if(optionalUsers.isPresent()) {
            Users users=optionalUsers.get();
            List<Commande> commandeList = commandeRepository.findByUserAndDeleteAtNull(users);
            return commandeList.stream()
                    .map(commandeConverter::toDto)
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Utiisateur non trouvé");
    }


    @Override
    public CommandeDto updateEtatCommande(UUID commande_id, CommandeDto commandeDto) {
        Optional<Commande> optionalCommande=commandeRepository.findByIdAndDeleteAtIsNull(commande_id);
        if(optionalCommande.isPresent()) {

            Commande commande = optionalCommande.get();
            commande.setEtat(commandeDto.getEtat());
            return commandeConverter.toDto(commandeRepository.save(commande));
        }
        else
            return null;
    }

    @Override
    public CommandeDto updatePanier(UUID comandeId, CommandeDto commandeDto) {
        Optional<Commande> optionalCommande=commandeRepository.findByIdAndDeleteAtIsNull(comandeId);
        double som=0.0;
        if(optionalCommande.isPresent()) {
            List<Produits> produitsList=new ArrayList<>();
            List<UUID> uuidList=new ArrayList<>(commandeDto.getProductQuantite().keySet());

            for(UUID id:uuidList){
                Optional<Produits> produits=produitsRepository.findByIdAndDeleteAtIsNull(id);
                produits.ifPresent(produitsList::add);
            }
            Commande commande = optionalCommande.get();
            commande.setProduits(produitsList);
            for(Map.Entry<UUID ,Integer> entry:commandeDto.getProductQuantite().entrySet()){
                Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(entry.getKey());
                if(optionalProduits.isPresent()) {
                    Produits produits= optionalProduits.get();
                    int resteEnStock = optionalProduits.get().getQuantiteStock();
                    if (resteEnStock - entry.getValue() >= 0){
                        som += entry.getValue() * optionalProduits.get().getPrixUnitaire();
                        resteEnStock=resteEnStock - entry.getValue();
                        produits.setQuantiteStock(resteEnStock);
                        produitsRepository.save(produits);
                    }
                    else
                        throw new QuantitiesOutOfBoundExceptions("Quantité en stock pour un produit spécifié dans la commande non suffisante");
                }
                else
                    throw new IllegalArgumentException("Un Produit n'a pas été trouvé");
            }
            commande.setPrixTotal(som);
            return commandeConverter.toDto(commande);
        }
        else
            return null;
    }

    @Override
    public boolean deleteCommande(UUID id) {
        Optional<Commande> optionalCommande=commandeRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalCommande.isPresent()){
            Commande commande= optionalCommande.get();
            commande.setDeleteAt(LocalDateTime.now());
            commande.setEtat(Etat.ANNULE);
            commandeRepository.save(commande);
            return true;
        }
        return false;
    }
}
