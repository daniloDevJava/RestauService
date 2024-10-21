package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.ProduitsDto;
import com.projet.foodGo.model.Produits;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class ProduitsConverter {

    public Produits toEntity(ProduitsDto produitsDto){
        if(produitsDto==null)
            return null;
        Produits produits=new Produits();
        produits.setLibelle(produitsDto.getLibelle());
        produits.setPrixUnitaire(produitsDto.getPrixUnitaire());
        produits.setTypeProduct(produitsDto.getTypeProduct());
        produits.setQuantiteStock(produitsDto.getQuantiteStock());
        return produits;
    }
    public ProduitsDto toDto(Produits produits){
        if(produits==null)
            return null;
        ProduitsDto produitsDto=new ProduitsDto();
        produitsDto.setId(produits.getId());
        produitsDto.setQuantiteStock(produits.getQuantiteStock());
        produitsDto.setPrixUnitaire(produits.getPrixUnitaire());
        produitsDto.setTypeProduct(produits.getTypeProduct());
        produitsDto.setLibelle(produits.getLibelle());
        return produitsDto;
    }
    public List<ProduitsDto> toDtoList(List<Produits> produits){
        if(produits.isEmpty())
            return new ArrayList<>();
        List<ProduitsDto> listProduits=new LinkedList<>();
        for(Produits produits1:produits){
            listProduits.add(toDto(produits1));
        }
        return listProduits;
    }

    public List<Produits> toEntityList(List<ProduitsDto> listProduits) {
        if(listProduits.isEmpty())
            return new ArrayList<>();
        List<Produits> produits=new LinkedList<>();
        for(ProduitsDto produitsDto:listProduits){
            produits.add(toEntity(produitsDto));
        }
        return produits;
    }
}
