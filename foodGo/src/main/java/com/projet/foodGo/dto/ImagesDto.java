package com.projet.foodGo.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ImagesDto {

    private Long id;
    private String chemin;
    private String nom;

    private void makeName(){
        this.nom="IMG"+ 0 +id;
    }
}
