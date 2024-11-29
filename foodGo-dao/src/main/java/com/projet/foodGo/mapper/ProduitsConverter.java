package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.ProduitsDto;
import com.projet.foodGo.model.Images;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.repository.ImagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@AllArgsConstructor
public class ProduitsConverter {
    private final ImagesRepository imagesRepository;

    public Produits toEntity(ProduitsDto produitsDto){
        if(produitsDto==null)
            return null;
        Produits produits=new Produits();
        List<Images> imagesList=new ArrayList<>();
        produits.setLibelle(produitsDto.getLibelle());
        produits.setPrixUnitaire(produitsDto.getPrixUnitaire());
        produits.setQuantiteStock(produitsDto.getQuantiteStock());
        if(produitsDto.getImagesList()!=null) {
            for (Long id : produitsDto.getImagesList()) {
                Optional<Images> optionalImages = imagesRepository.findByIdAndDeleteAtIsNull(id);
                optionalImages.ifPresent(imagesList::add);
            }
        }
        produits.setImagesList(imagesList);
        return produits;
    }
    public ProduitsDto toDto(Produits produits){
        if(produits==null)
            return null;
        ProduitsDto produitsDto=new ProduitsDto();
        List<Long> imagesList=new ArrayList<>();
        produitsDto.setId(produits.getId());
        produitsDto.setQuantiteStock(produits.getQuantiteStock());
        produitsDto.setPrixUnitaire(produits.getPrixUnitaire());
        produitsDto.setCreateAt(produits.getCreateAt());
        produitsDto.setUpdateAt(produits.getUpdateAt());
        for(Images images: produits.getImagesList()){
            imagesList.add(images.getId());
        }
        produitsDto.setImagesList(imagesList);
        produitsDto.setLibelle(produits.getLibelle());
        return produitsDto;
    }

}
