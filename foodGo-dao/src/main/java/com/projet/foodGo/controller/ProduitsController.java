package com.projet.foodGo.controller;

import com.projet.foodGo.dto.ProduitsDto;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.repository.ProduitsRepository;
import com.projet.foodGo.service.ProduitsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/produits")
@AllArgsConstructor
public class ProduitsController {
    private final ProduitsService produitsService;
    private final ProduitsRepository produitsRepository;

    @PostMapping("/add")
    @Operation(summary = "create product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Product created"),
            @ApiResponse(responseCode = "404",description = "Prestataire is not found"),
            @ApiResponse(responseCode = "500",description = "Not same libelle for the same prestataire")
    })
    public ResponseEntity<ProduitsDto> createProduct(@Parameter(description = "Id of Prestataire") @RequestParam UUID prestataireId, @RequestBody ProduitsDto produitsDto){
        try{
            ProduitsDto produit=produitsService.createProduct(produitsDto, prestataireId);
            System.out.printf("%s",produit);
            if(produit!=null) {
                return new ResponseEntity<>(produit, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    @Operation(summary = "get all products")
    @ApiResponse(responseCode = "200",description = "the all list")
    public ResponseEntity<List<ProduitsDto>> getProducts(){
        return new ResponseEntity<>(produitsService.getProducts(),HttpStatus.OK);
    }

    @GetMapping("/all-of-one")
    @Operation(summary = "get list's products of prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500",description = "prestataire doesn't exists"),
            @ApiResponse(responseCode = "200",description = "the found list")
    })
    public ResponseEntity<List<ProduitsDto>> getProducts(@Parameter(description = "Id Of Prestataire") @RequestParam UUID prestataireId){
        try{
            return new ResponseEntity<>(produitsService.getProducts(prestataireId),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/corbeille-of-one")
    @Operation(summary = "get corbeille of prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the corbeille of products for an preatataire"),
            @ApiResponse(responseCode = "500",description = "the prestataire is not found")
    })
    public ResponseEntity<List<ProduitsDto>> getCorbeille(@Parameter(description = "ID Of Prestataire") @RequestParam UUID prestataireId){
        try{
            return new ResponseEntity<>(produitsService.getCorbeille(prestataireId),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    @Operation(summary = "get product")
    @ApiResponse(responseCode = "200",description = "the found product")
    public ResponseEntity<ProduitsDto> getProduct(@Parameter(description = "Id of product") @PathVariable UUID id){
        ProduitsDto produit= produitsService.getProduct(id);
        if(produit!=null)
            return new ResponseEntity<>(produit,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{nom}/getByName")
    @Operation(summary = "get product")
    @ApiResponse(responseCode = "200",description = "the found product")
    public ResponseEntity<ProduitsDto> getProduct(@Parameter(description = "name of Product") @PathVariable String nom,@Parameter(description = "ID of Prestataire") @RequestParam UUID id){
        try {
            ProduitsDto produit = produitsService.getProduct(nom, id);
            if (produit != null)
                return new ResponseEntity<>(produit, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    @Operation(summary = "full update of product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "everything is up-to-ddate"),
            @ApiResponse(responseCode = "404",description = "product is not found")
    })
    public ResponseEntity<ProduitsDto> updateProduct(@Parameter(description = "Id of Product") @PathVariable UUID id,@RequestBody ProduitsDto produitsDto){
        ProduitsDto produit=produitsService.updateProduct(id,produitsDto);
        if(produit!=null)
            return new ResponseEntity<>(produit,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}/update-quantity")
    @Operation(summary = "full update of product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "everything is up-to-ddate"),
            @ApiResponse(responseCode = "404",description = "product is not found")
    })
    public ResponseEntity<ProduitsDto> updateProductQuantiteStock(@Parameter(description = "Id of Product") @PathVariable UUID id,@RequestBody ProduitsDto produitsDto){
        ProduitsDto produit=produitsService.updateQuantiteStock(id,produitsDto);
        if(produit!=null)
            return new ResponseEntity<>(produit,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/restaure/{id}")
    @Operation(summary = "Restore product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Le produit a été restauré"),
            @ApiResponse(responseCode = "404",description = "Le produit n'est pas trouvé")
    })
    public ResponseEntity<String> restoreProduct(@Parameter(description = "Id OF PRoduct") @PathVariable UUID id){
        Optional<Produits> optionalProduits=produitsRepository.findById(id);
        if(optionalProduits.isPresent()){
            Produits produits= optionalProduits.get();
            produits.setDeleteAt(null);
            produitsRepository.save(produits);
            return new ResponseEntity<>("{\"message\" : \" "+ produits.getLibelle()+ " is restored successfully\"}",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("{\"message\" : \"product doesn't exists\"}",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete an product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "product is deleted"),
            @ApiResponse(responseCode = "404",description = "product is not found")
    })
    public ResponseEntity<String> deleteProduit(@Parameter(description = "Id of Product") @PathVariable UUID id){
        if(produitsService.deleteProduct(id))
            return new ResponseEntity<>("{\"message\" : \" product is deleted successfully\"}",HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"message\" : \"product doesn't exists\"}",HttpStatus.NOT_FOUND);
    }
}
