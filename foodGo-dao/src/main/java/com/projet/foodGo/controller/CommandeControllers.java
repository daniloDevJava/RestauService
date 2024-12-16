package com.projet.foodGo.controller;

import com.projet.foodGo.dto.CommandeDto;
import com.projet.foodGo.service.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dao/commandes")
@RequiredArgsConstructor
public class CommandeControllers {
    private final CommandeService  commandeService;

    @PostMapping("/add")
    @Operation(summary = " try a commande by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "THe processus of commande has begin"),
            @ApiResponse(responseCode = "500",description = "The commande doesn't respects the parameters")
    })
    public ResponseEntity<CommandeDto> createCommande(@Valid @RequestBody CommandeDto commandeDto, @Parameter(description = "Id of user") @RequestParam("userId")UUID userId,@Parameter(description = "Id of Prestataire") @RequestParam("prestataireId") UUID prestataireId){
        try {
            CommandeDto commande=commandeService.createCommande(commandeDto,userId,prestataireId);
            return new ResponseEntity<>(commande,HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-by-user")
    @Operation(summary = "recuperer toutes les commandes initiées par un user donné")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "LA liste recherchée"),
            @ApiResponse(responseCode = "500",description = "L'user n'est pas trouvé")
    })
    public ResponseEntity<List<CommandeDto>> getAllByUser(@Parameter(description = "Id Of User") @RequestParam("userId") UUID userId){
        try {
            return new ResponseEntity<>(commandeService.getCommandesByUser(userId),HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all-by-prestataire")
    @Operation(summary = "recuperer toutes les commandes dont  un prestataire donné en a été la cible")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "LA liste recherchée"),
            @ApiResponse(responseCode = "500",description = "L'user n'est pas trouvé")
    })
    public ResponseEntity<List<CommandeDto>> getAllByPrestataire(@Parameter(description = "Id Of prestataire") @RequestParam("prestataireId") UUID prestataireId){
        try {
            return new ResponseEntity<>(commandeService.getCommandes(prestataireId),HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "get an commande")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found commande"),
            @ApiResponse(responseCode = "404",description = "Commande is not found")
    })
    public ResponseEntity<CommandeDto> getCommande(@Parameter(description = "Id of commande") @PathVariable UUID commandeId){
        CommandeDto commande= commandeService.getCommande(commandeId);
        if(commande!=null)
            return new ResponseEntity<>(commande,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/state-change")
    @Operation(summary = "Update state of commande ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "state of this commande is up-to-date"),
            @ApiResponse(responseCode = "404",description = "The commande is not found")
    })
    public ResponseEntity<CommandeDto> updateCommande(@Parameter(description = "Id Of Commande") @PathVariable UUID id,@Valid @RequestBody CommandeDto commandeDto){
        CommandeDto commande= commandeService.updateEtatCommande(id,commandeDto);
        if(commande!=null)
            return new ResponseEntity<>(commande,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/panier-change")
    @Operation(summary = "Update state of commande ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "state of this commande is up-to-date"),
            @ApiResponse(responseCode = "404",description = "The commande is not found")
    })
    public ResponseEntity<CommandeDto> updatePanier(@Parameter(description = "Id Of Commande") @PathVariable UUID commandeId,@Valid @RequestBody CommandeDto commandeDto){
        CommandeDto commande= commandeService.updatePanier(commandeId,commandeDto);
        if(commande!=null)
            return new ResponseEntity<>(commande,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete an commande")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "DEleting is okay"),
            @ApiResponse(responseCode = "404",description = "commande doesn't exists")
    })
    public ResponseEntity<String> deleteCommande(@Parameter(description = "Id Of Commande") @PathVariable UUID commandeId){
        if(commandeService.deleteCommande(commandeId))
            return new ResponseEntity<>("{\"message\" : \"commande is deleted successfully\"}",HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"message\" : \"commamnde doesn't exists\"}",HttpStatus.NOT_FOUND);
    }


}
