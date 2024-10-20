package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.ProduitAlimentaireDto;
import com.projet.foodGo.model.ProduitAlimentaire;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class ProduitAlimentaireConverter {

    public ProduitAlimentaire toEntity(ProduitAlimentaireDto produitAlimentaireDto){
        if(produitAlimentaireDto==null)
            return null;
        ProduitAlimentaire produitAlimentaire=new ProduitAlimentaire();
        produitAlimentaire.setLibelle(produitAlimentaireDto.getLibelle());
        produitAlimentaire.setPrixUnitaire(produitAlimentaireDto.getPrixUnitaire());
        produitAlimentaire.setQuantiteStock(produitAlimentaireDto.getQuantiteStock());
        return produitAlimentaire;
    }
    public ProduitAlimentaireDto toDto(ProduitAlimentaire produitAlimentaire){
        if(produitAlimentaire==null)
            return null;
        ProduitAlimentaireDto produitAlimentaireDto=new ProduitAlimentaireDto();
        produitAlimentaireDto.setId(produitAlimentaire.getId());
        produitAlimentaireDto.setQuantiteStock(produitAlimentaire.getQuantiteStock());
        produitAlimentaireDto.setLibelle(produitAlimentaire.getLibelle());
        return produitAlimentaireDto;
    }
    public List<ProduitAlimentaireDto> toDtoList(List<ProduitAlimentaire> produits){
        if(produits.isEmpty())
            return new ArrayList<>();
        List<ProduitAlimentaireDto> listProduits=new LinkedList<>();
        for(ProduitAlimentaire produitAlimentaire:produits){
            listProduits.add(toDto(produitAlimentaire));
        }
        return listProduits;
    }

    public List<ProduitAlimentaire> toEntityList(List<ProduitAlimentaireDto> listProduits) {
        if(listProduits.isEmpty())
            return new ArrayList<>();
        List<ProduitAlimentaire> produits=new LinkedList<>();
        for(ProduitAlimentaireDto produitAlimentaireDto:listProduits){
            produits.add(toEntity(produitAlimentaireDto));
        }
        return produits;
    }
}
