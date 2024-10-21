package com.projet.foodGo.dto;


import com.projet.foodGo.model.enumType.NatureCompte;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PrestataireDto extends UserDto {

    private List<ProduitsDto> listProduits;
    private Double noteMoyenne;
    private NatureCompte natureCompte;
    private String cheminVersImages;


    public List<ProduitsDto> getListProduits() {
        return listProduits;
    }

    public void setListProduits(List<ProduitsDto> listProduits) {
        this.listProduits = listProduits;
    }

    public Double getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(Double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    public NatureCompte getNatureCompte() {
        return natureCompte;
    }

    public void setNatureCompte(NatureCompte natureCompte) {
        this.natureCompte = natureCompte;
    }

    public String getCheminVersImages() {
        return cheminVersImages;
    }

    public void setCheminVersImages(String cheminVersImages) {
        this.cheminVersImages = cheminVersImages;
    }

    //private Location geography;


}
