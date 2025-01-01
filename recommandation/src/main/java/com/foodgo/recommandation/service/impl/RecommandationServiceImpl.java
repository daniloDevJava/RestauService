package com.foodgo.recommandation.service.impl;

import com.foodgo.recommandation.dto.Coordinates;
import com.foodgo.recommandation.dto.Coordonnees;
import com.foodgo.recommandation.dto.RestaurantScore;
import com.foodgo.recommandation.exceptions.BusinessException;
import com.foodgo.recommandation.dto.Distance;
import com.foodgo.recommandation.exceptions.ErrorModel;
import com.foodgo.recommandation.external.PrestataireDto;
import com.foodgo.recommandation.service.RecommandationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class RecommandationServiceImpl implements RecommandationService {
    private final WebClient.Builder webClientBuilder;


    @Value("${geolocation.service.endpoint.url}") // Injectez l'URL de l'API dans les propriétés
    private String geolocationApiUrl;

    PriorityQueue<RestaurantScore> maxHeap=new PriorityQueue<>(Comparator.comparingDouble(RestaurantScore::getScore));

    @Override
    public PriorityQueue<RestaurantScore> recommandRestaurant(String libelle, Coordinates coordinates) {

        List<PrestataireDto> restaurants=webClientBuilder.build()
                .get()
                .uri("/prestataires-find?libelle="+libelle)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PrestataireDto>>() {
                })
                .block();

        assert restaurants != null;
        for(PrestataireDto restaurant: restaurants){
                Coordinates coordinatesRestaurant=new Coordinates(restaurant.getLatitude(), restaurant.getLongitude());
                double score=restaurant.getNoteMoyenne()*0.4 + (1/calculateDistance(coordinatesRestaurant,coordinates))*0.6;
                System.err.println(score + restaurant.getNom());
                RestaurantScore restaurantScore=new RestaurantScore(restaurant,score);
                maxHeap.add(restaurantScore);

        }

        return maxHeap;
    }

    private double calculateDistance(Coordinates coordinatesRestaurant,Coordinates coordinatesUser){
        Coordonnees coordonnees=new Coordonnees();
        coordonnees.setLat1(coordinatesUser.getLatitude());
        coordonnees.setLon1(coordinatesUser.getLongitude());
        coordonnees.setLat2(coordinatesRestaurant.getLatitude());
        coordonnees.setLon2(coordinatesRestaurant.getLongitude());
        
        System.out.println("lat1"+coordonnees.getLat1()+" lon1"+coordonnees.getLon1()+"lat2 "+coordonnees.getLat2()+"lon2: "+coordonnees.getLon2());

        Distance distance=webClientBuilder.build().post()
                .uri(geolocationApiUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(coordonnees)
                .retrieve()
                .onStatus(
                        status -> status.value() >= 400 && status.value() < 500, // Vérifie les erreurs 4xx
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    ErrorModel errorModel = new ErrorModel();
                                    errorModel.setCode("CLIENT_ERROR");
                                    errorModel.setMessage("Erreur lors de l'appel au service DAO : " + errorBody);
                                    return Mono.error(new BusinessException(List.of(errorModel)));
                                })
                )
                .onStatus(
                        status -> status.value() >= 500, // Vérifie les erreurs 5xx
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    ErrorModel errorModel = new ErrorModel();
                                    errorModel.setCode("SERVER_ERROR");
                                    errorModel.setMessage("Erreur interne dans le service DAO : " + errorBody);
                                    return Mono.error(new BusinessException(List.of(errorModel)));
                                })
                )
                .bodyToMono(Distance.class)
                .block();


        assert distance != null;
        System.err.println(distance.getDistance_km());
        return distance.getDistance_km();


    }

    @Override
    public PriorityQueue<RestaurantScore> recommandRestaurant(Coordinates coordinates) {

        List<PrestataireDto> restaurants=webClientBuilder.build()
                .get()
                .uri("/all")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<PrestataireDto>>() {
                })
                .block();

        assert restaurants != null;
        for(PrestataireDto restaurant: restaurants){
                Coordinates coordinatesRestaurant=new Coordinates(restaurant.getLatitude(), restaurant.getLongitude());
                double score=restaurant.getNoteMoyenne()*0.4 + (1/calculateDistance(coordinatesRestaurant,coordinates))*0.6;
                System.err.println(score + restaurant.getNom());
                RestaurantScore restaurantScore=new RestaurantScore(restaurant,score);
                maxHeap.add(restaurantScore);

        }

        return maxHeap;
    }
}




