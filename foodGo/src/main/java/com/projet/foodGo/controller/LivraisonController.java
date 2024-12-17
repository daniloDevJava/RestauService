package com.projet.foodGo.controller;


import com.projet.foodGo.dto.LivraisonDto;
import com.projet.foodGo.service.LivraisonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/livraisons")
@RequiredArgsConstructor
public class LivraisonController {
    private final LivraisonService livraisonService;

    @PostMapping("/add")
    @Operation(summary = "Propose an livraison")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "the new livraison"),
            @ApiResponse(responseCode = "500",description = "The Commande is not found")
    })
    public ResponseEntity<LivraisonDto> createLivraison(@Parameter(description = "Id Of Commande")@RequestParam("commandeId")UUID commandeId, @RequestBody LivraisonDto livraisonDto){
        try {
            LivraisonDto livraison = livraisonService.createLivraison(livraisonDto, commandeId);
            return new ResponseEntity<>(livraison,HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e){
            LivraisonDto livraisonDto1=new LivraisonDto();
            livraisonDto1.setNumeroLivreur(e.getMessage());
            return new ResponseEntity<>(livraisonDto1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{livraisonId}")
    @Operation(summary = "get an livraison")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "The found Livraison"),
            @ApiResponse(responseCode = "404",description = "Livraison is not found")
    })
    public ResponseEntity<LivraisonDto> getLivraison(@Parameter(description = "Id Of Livraison") @PathVariable UUID livraisonId){
        LivraisonDto livraison= livraisonService.getLivraison(livraisonId);
        if(livraison!=null)
            return new ResponseEntity<>(livraison,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/reference-commande")
    @Operation(summary = "get an livraison")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "The found Livraison"),
            @ApiResponse(responseCode = "404",description = "Livraison is not found")
    })
    public ResponseEntity<LivraisonDto> getLivraisonByCommande(@Parameter(description = "Id Of Commande") @RequestParam("commandeId") UUID commandeId){
        LivraisonDto livraison= livraisonService.getLivraisonByCommande(commandeId);
        if(livraison!=null)
            return new ResponseEntity<>(livraison,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update livraison")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "everything is up-to-date"),
            @ApiResponse(responseCode = "404",description = "Livraison is not found")
    })
    public ResponseEntity<LivraisonDto> updateLivraison(@Parameter(description = "Id Of Livraison") @PathVariable UUID id,@RequestBody LivraisonDto livraisonDto){
        LivraisonDto livraison=livraisonService.updateLivraison(id,livraisonDto);
        if(livraison!=null)
            return new ResponseEntity<>(livraison,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/change-state")
    @Operation(summary = "update livraison")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "everything is up-to-date"),
            @ApiResponse(responseCode = "404",description = "Livraison is not found")
    })
    public ResponseEntity<LivraisonDto> updateEtatLivraison(@Parameter(description = "Id Of Livraison") @PathVariable UUID id,@RequestBody LivraisonDto livraisonDto){
        LivraisonDto livraison=livraisonService.updateEtat(id,livraisonDto);
        if(livraison!=null)
            return new ResponseEntity<>(livraison,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete an livraison")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "deleting is okay"),
            @ApiResponse(responseCode = "404",description = "LIVRAISON IS NOT FOUND")
    })
    public ResponseEntity<String> deleteLivraison(@Parameter(description = "Id Of Livraison") @PathVariable UUID id){
        if(livraisonService.deleteLivraison(id))
            return new ResponseEntity<>("{\"message\" : \"livraison is deleted successfully\"}",HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"message\" : \"livraison doesn't exists\"}",HttpStatus.NOT_FOUND);
    }
}
