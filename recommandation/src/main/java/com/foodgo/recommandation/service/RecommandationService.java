package com.foodgo.recommandation.service;

import com.foodgo.recommandation.dto.Coordinates;
import com.foodgo.recommandation.dto.RestaurantScore;
import com.foodgo.recommandation.external.PrestataireDto;


import java.util.PriorityQueue;


public interface RecommandationService {
    PriorityQueue<RestaurantScore> recommandRestaurant(String libelle, Coordinates coordinates);
    PriorityQueue<RestaurantScore> recommandRestaurant(Coordinates coordinates);
}
