package com.foodgo.recommandation.external;

import com.foodgo.recommandation.external.enumType.NatureCompte;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PrestataireDto extends UserDto {

    private List<UUID> listProduits;
    private Double noteMoyenne;
    private NatureCompte natureCompte;
    private Double longitude;
    private Double latitude;
    private double montantCompte;
    private String address;

}
