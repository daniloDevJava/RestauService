package com.projet.foodGo.dto;


import com.projet.foodGo.model.enumType.NatureCompte;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;


import java.util.List;



@Getter
@Setter
public class PrestataireDto extends UserDto {

    private List<ProduitsDto> listProduits;
    private Double noteMoyenne;
    private NatureCompte natureCompte;

    private Double longitude;
    private Double latitude;



}
