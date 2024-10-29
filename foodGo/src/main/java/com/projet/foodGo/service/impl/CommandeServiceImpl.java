package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.CommandeDto;
import com.projet.foodGo.mapper.CommandeConverter;
import com.projet.foodGo.model.Commande;
import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.model.Users;
import com.projet.foodGo.repository.CommandeRepository;
import com.projet.foodGo.repository.PrestataireRepository;
import com.projet.foodGo.repository.ProduitsRepository;
import com.projet.foodGo.repository.UserRepository;
import com.projet.foodGo.service.CommandeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private final PrestataireRepository prestataireRepository;
    private final CommandeRepository commandeRepository;
    private final CommandeConverter commandeConverter;
    private final ProduitsRepository produitsRepository;
    private final UserRepository userRepository;


    public boolean isFound(CommandeDto commandeDto) {
        List<UUID> uuidListProducts=commandeDto.getProduits();
        List<Integer> listQuantities=commandeDto.getQuantiteesCommandees();
        if(listQuantities.size()== uuidListProducts.size()){
            for(int i=0;i<listQuantities.size();i++){
                Optional<Produits> optionalproduits=produitsRepository.findByIdAndDeleteAtIsNull(uuidListProducts.get(i));
                if(optionalproduits.isPresent())
                {
                    if(optionalproduits.get().getQuantiteStock()- listQuantities.get(i)>=0) {

                        return true;
                    }
                    else {
                        break;
                    }

                }
                throw new IllegalArgumentException("produit non trouvé");
            }
        }
        else
            throw  new IllegalArgumentException("Il ne doit pas y avoir plus de produits que de quantités demandées et vice-versa");
        return false;
    }


    @Override
    public CommandeDto createCommande(CommandeDto commandeDto, UUID user_id, UUID prestataireId) {
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(user_id);
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(prestataireId);

        if(optionalPrestataire.isPresent() && optionalUsers.isPresent())
        {
            commandeDto.setIdUser(user_id);
            commandeDto.setIdPrestataire(prestataireId);
            Commande commande=commandeConverter.toEntity(commandeDto);
            if(commandeDto.getProduits().size()!=commandeDto.getQuantiteesCommandees().size())
                throw new IllegalArgumentException("Il ne doit pas y avoir plus de produits que de quantités demandées et vice-versa");
            else{
                Map<UUID,Integer> map=new HashMap<>();

                for(Produits produits: commande.getProduits()){

                }

            }

        }
        return null;
    }

    /**
     * @param id 
     * @return
     */
    @Override
    public CommandeDto getCommande(UUID id) {
        return null;
    }

    /**
     * @param idPrestataire 
     * @return
     */
    @Override
    public List<CommandeDto> getCommandes(UUID idPrestataire) {
        return List.of();
    }

    /**
     * @param user_id 
     * @return
     */
    @Override
    public List<CommandeDto> getCommandesByUser(UUID user_id) {
        return List.of();
    }

    /**
     * @param commande_id 
     * @param commandeDto
     * @return
     */
    @Override
    public CommandeDto updateCommandDto(UUID commande_id, CommandeDto commandeDto) {
        return null;
    }

    /**
     * @param commande_id 
     * @param commandeDto
     * @return
     */
    @Override
    public CommandeDto updateEtatCommande(UUID commande_id, CommandeDto commandeDto) {
        return null;
    }

    /**
     * @param id 
     * @param commandeDto
     * @return
     */
    @Override
    public CommandeDto updatePanier(UUID id, CommandeDto commandeDto) {
        return null;
    }

    /**
     * @param id 
     * @return
     */
    @Override
    public boolean deleteCommande(UUID id) {
        return false;
    }
}
