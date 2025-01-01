package com.foodgo.recommandation.controller;

import com.foodgo.recommandation.dto.Coordinates;
import com.foodgo.recommandation.dto.RestaurantScore;
import com.foodgo.recommandation.external.PrestataireDto;
import com.foodgo.recommandation.service.RecommandationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommandationController {

    private final RecommandationService recommandationService;

    @PostMapping("/with-food")
    @Operation(summary = "la recommandation de restaurant en entrant la nourriture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "la liste triée de restaurants recommandées pqr ordre de mérite "),
            @ApiResponse(responseCode = "500",description = "Cette nourriture n'est pas trouvée")
    }
    )
    public ResponseEntity<List<PrestataireDto>> recommendationFood(@RequestParam String libelle, @RequestBody Coordinates coordinates){
        List<PrestataireDto> listRestaurant=new ArrayList<>();
        PriorityQueue<RestaurantScore> maxHeap=recommandationService.recommandRestaurant(libelle,coordinates);
         while (!maxHeap.isEmpty()){

             listRestaurant.addFirst(maxHeap.poll().getPrestataireDto());
         }

        return new ResponseEntity<>(listRestaurant, HttpStatus.OK);
    }

    @PostMapping("/show")
    @Operation(summary = "la recommandation de restaurant en entrant la nourriture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "la liste triée de restaurants recommandées pqr ordre de mérite "),
            @ApiResponse(responseCode = "500",description = "Cette nourriture n'est pas trouvée")
    }
    )
    public ResponseEntity<List<PrestataireDto>> recommendationFood(@RequestBody Coordinates coordinates){
        List<PrestataireDto> listRestaurant=new ArrayList<>();
        PriorityQueue<RestaurantScore> maxHeap=recommandationService.recommandRestaurant(coordinates);
         while (!maxHeap.isEmpty()){

             listRestaurant.addFirst(maxHeap.poll().getPrestataireDto());
         }

        return new ResponseEntity<>(listRestaurant, HttpStatus.OK);
    }
}
