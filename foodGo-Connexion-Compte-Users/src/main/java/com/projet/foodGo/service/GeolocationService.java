package com.projet.foodGo.service;
import com.projet.foodGo.external.GeoCoordinates;
import org.springframework.beans.factory.annotation.Value; // Pour injecter l'URL de l'API depuis application.properties ou application.yml
import org.springframework.http.HttpHeaders; // Pour définir les en-têtes HTTP
import org.springframework.http.MediaType; // Pour spécifier le type de contenu (JSON)
import org.springframework.stereotype.Service; // Pour déclarer cette classe comme un service Spring
import org.springframework.web.reactive.function.client.WebClient; // Pour effectuer des appels HTTP non bloquants

import java.util.Collections;

@Service
public class GeolocationService {

    private final WebClient webClient;

    @Value("${geolocation.api.url}") // Injectez l'URL de l'API dans les propriétés
    private String geolocationApiUrl;

    public GeolocationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * Appelle le service de géolocalisation pour récupérer les coordonnées.
     *
     * @param nom Adresse ou quartier.
     * @return Un objet contenant la latitude et la longitude.
     */
    public GeoCoordinates getCoordinates(String nom) {
        try {
            return webClient.post()
                    .uri(geolocationApiUrl) // URL de l'API
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(Collections.singletonMap("nom", nom)) // Corps de la requête
                    .retrieve()
                    .bodyToMono(GeoCoordinates.class) // Convertir la réponse en objet Java
                    .block(); // Bloquer pour obtenir une réponse synchrone
        } catch (Exception e) {
            throw new IllegalArgumentException
            
            ("Erreur lors de la récupération des coordonnées : " + e.getMessage());
        }
    }
}

