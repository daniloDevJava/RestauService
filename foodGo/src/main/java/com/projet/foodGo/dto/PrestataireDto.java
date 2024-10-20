package com.projet.foodGo.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PrestataireDto extends UserDto {


    private List<ProduitAlimentaireDto> listProduits;
    private Double noteMoyenne;

    public List<ProduitAlimentaireDto> getListProduits() {
        return listProduits;
    }

    public void setListProduits(List<ProduitAlimentaireDto> listProduits) {
        this.listProduits = listProduits;
    }

    public Double getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(Double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    //private Location geography;


}
