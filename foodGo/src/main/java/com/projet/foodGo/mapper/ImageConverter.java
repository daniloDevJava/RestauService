package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.ImagesDto;
import com.projet.foodGo.model.Images;
import org.springframework.stereotype.Component;



@Component
public class ImageConverter {
    public Images toEntity(ImagesDto imagesDto){
        if(imagesDto==null)
            return null;
        Images image=new Images();
        image.setNom(imagesDto.getNom());
        image.setChemin(imagesDto.getChemin());
        return image;
    }
    public ImagesDto toDto(Images images){
        if(images==null)
            return null;
        ImagesDto imagesDto=new ImagesDto();
        imagesDto.setChemin(images.getChemin());
        imagesDto.setId(images.getId());
        imagesDto.setNom(images.getNom());
        return imagesDto;
    }
}
