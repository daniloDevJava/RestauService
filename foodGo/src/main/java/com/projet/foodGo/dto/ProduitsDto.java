package com.projet.foodGo.dto;


import com.projet.foodGo.model.enumType.TypeProduct;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class ProduitsDto {

    private UUID id;
    private int quantiteVenduLocal;
    private String libelle;
    private String cheminVersImages;
    private int quantiteStock;
    private int prixUnitaire;
    private List<Long> imagesList;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private TypeProduct typeProduct;


}
