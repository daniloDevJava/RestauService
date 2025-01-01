package com.foodgo.recommandation.dto;

import com.foodgo.recommandation.external.PrestataireDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantScore {
    PrestataireDto prestataireDto;
    double score;

    public RestaurantScore(PrestataireDto restaurant, double score) {
        prestataireDto=restaurant;
        this.score=score;
    }
}
