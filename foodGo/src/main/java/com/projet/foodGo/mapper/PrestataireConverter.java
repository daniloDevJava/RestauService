package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.PrestataireDto;
import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.repository.ProduitsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class PrestataireConverter {

    private final ProduitsRepository produitsRepository;

    public Prestataire toEntity(PrestataireDto prestataireDto){
        if(prestataireDto==null)
            return null;
        List<Produits> listProduits=new ArrayList<>();
        Point geography=new Point(prestataireDto.getLongitude(), prestataireDto.getLatitude());
        Prestataire prestataire=new Prestataire();
        prestataire.setAdresseMail(prestataireDto.getAdresseMail());
        prestataire.setNom(prestataireDto.getNom());
        prestataire.setNatureCompte(prestataireDto.getNatureCompte());
        prestataire.setGeography(geography);
        for(UUID id: prestataireDto.getListProduits()){
            Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(id);
            optionalProduits.ifPresent(listProduits::add);
        }
        prestataire.setListProduits(listProduits);
        prestataire.setNoteMoyenne(prestataireDto.getNoteMoyenne());
        prestataire.setMotDePasse(prestataireDto.getMotDePasse());
        return prestataire;
    }

    public PrestataireDto toDto(Prestataire prestataire){
        if(prestataire==null)
            return null;
        List<UUID> listProduits=new ArrayList<>();
        PrestataireDto prestataireDto=new PrestataireDto();
        prestataireDto.setId(prestataire.getId());
        prestataireDto.setNom(prestataire.getNom());
        prestataireDto.setAdresseMail(prestataire.getAdresseMail());
        prestataireDto.setNoteMoyenne(prestataire.getNoteMoyenne());
        prestataireDto.setCreateAt(prestataire.getCreateAt());
        prestataireDto.setUpdateAt(prestataire.getUpdateAt());
        prestataireDto.setNatureCompte(prestataire.getNatureCompte());
        prestataireDto.setLongitude(prestataire.getGeography().getX());
        prestataireDto.setLatitude(prestataire.getGeography().getY());
        prestataireDto.setMotDePasse(String.valueOf(prestataire.getMotDePasse().hashCode()));
        for(Produits produits: prestataire.getListProduits())
            listProduits.add(produits.getId());
        prestataireDto.setListProduits(listProduits);
        return prestataireDto;
    }

}
