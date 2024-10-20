package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.PrestataireDto;
import com.projet.foodGo.model.Prestataire;
import org.springframework.stereotype.Component;

@Component
public class PrestataireConverter {

    private  final ProduitAlimentaireConverter produitAlimentaireConverter;

    public PrestataireConverter(ProduitAlimentaireConverter produitAlimentaireConverter) {
        this.produitAlimentaireConverter = produitAlimentaireConverter;
    }

    public Prestataire toEntity(PrestataireDto prestataireDto){
        if(prestataireDto==null)
            return null;
        Prestataire prestataire=new Prestataire();
        prestataire.setAdresseMail(prestataireDto.getAdresseMail());
        prestataire.setNomEtPrenom(prestataireDto.getNomEtPrenom());
        prestataire.setListProduits(produitAlimentaireConverter.toEntityList(prestataireDto.getListProduits()));
        prestataire.setNoteMoyenne(prestataireDto.getNoteMoyenne());
        return prestataire;
    }
    public PrestataireDto toDto(Prestataire prestataire){
        if(prestataire==null)
            return null;
        PrestataireDto prestataireDto=new PrestataireDto();
        prestataireDto.setNomEtPrenom(prestataire.getNomEtPrenom());
        prestataireDto.setAdresseMail(prestataire.getAdresseMail());
        prestataireDto.setNoteMoyenne(prestataire.getNoteMoyenne());
        prestataireDto.setCreateAt(prestataire.getCreateAt());
        prestataireDto.setUpdateAt(prestataire.getUpdateAt());
        prestataireDto.setListProduits(produitAlimentaireConverter.toDtoList(prestataire.getListProduits()));
        return prestataireDto;
    }

}
