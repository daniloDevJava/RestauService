package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.PrestataireDto;
import com.projet.foodGo.model.Prestataire;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PrestataireConverter {

    private  final ProduitsConverter produitAlimentaireConverter;


    public Prestataire toEntity(PrestataireDto prestataireDto){
        if(prestataireDto==null)
            return null;
        Point geography=new Point(prestataireDto.getLongitude(), prestataireDto.getLatitude());
        Prestataire prestataire=new Prestataire();
        prestataire.setAdresseMail(prestataireDto.getAdresseMail());
        prestataire.setNom(prestataireDto.getNom());
        prestataire.setNatureCompte(prestataireDto.getNatureCompte());
        prestataire.setGeography(geography);
        prestataire.setListProduits(produitAlimentaireConverter.toEntityList(prestataireDto.getListProduits()));
        prestataire.setNoteMoyenne(prestataireDto.getNoteMoyenne());
        return prestataire;
    }
    public PrestataireDto toDto(Prestataire prestataire){
        if(prestataire==null)
            return null;
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
        prestataireDto.setListProduits(produitAlimentaireConverter.toDtoList(prestataire.getListProduits()));
        return prestataireDto;
    }

}
