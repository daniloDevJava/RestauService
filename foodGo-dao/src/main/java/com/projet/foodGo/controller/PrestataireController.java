package com.projet.foodGo.controller;

import com.projet.foodGo.dto.PrestataireDto;
import com.projet.foodGo.service.PrestataireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dao/prestataires")
@AllArgsConstructor
public class PrestataireController {

    private final PrestataireService prestataireService;

    @PostMapping("/add")
    @Operation(summary = "create prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "you have create new prestataire"),
            @ApiResponse(responseCode = "400", description = "A field(s) is missing")
    }
    )
    public ResponseEntity<PrestataireDto> createPrestataire(@Valid @RequestBody PrestataireDto prestataireDto) {
        PrestataireDto prestataire = prestataireService.createPrestataire(prestataireDto);
        return new ResponseEntity<>(prestataire, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get an prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "the found prestataire"),
            @ApiResponse(responseCode = "404", description = "the prestataire doesn't exists")
    })
    public ResponseEntity<PrestataireDto> getPrestataire(@Parameter(description = "id of prestTaire") @PathVariable UUID id) {
        PrestataireDto prestataireDto = prestataireService.getPrestataire(id);
        if (prestataireDto != null)
            return new ResponseEntity<>(prestataireDto, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @Operation(summary = "get all prestataires")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<PrestataireDto>> getPrestataires() {
        return new ResponseEntity<>(prestataireService.getPrestataires(), HttpStatus.OK);
    }

    @GetMapping("/{nom}/get-by-name")
    @Operation(description = "get a prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "the found prestataire"),
            @ApiResponse(responseCode = "404", description = "the prestataire doesn't exists")
    })
    public ResponseEntity<PrestataireDto> getPrestataire(@Parameter(description = "Nom du prestataire") @PathVariable String nom) {
        PrestataireDto prestataireDto = prestataireService.getPrestataire(nom);
        System.out.println(prestataireDto.getNatureCompte());
        if (prestataireDto != null)
            return new ResponseEntity<>(prestataireDto, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "full updating of prestataire"),
            @ApiResponse(responseCode = "404", description = "the prestataire doesn't exisits")
    })
    public ResponseEntity<PrestataireDto> updatePrestataire(@Parameter(description = "id of prestTaire") @PathVariable UUID id,@Valid @RequestBody PrestataireDto prestataireDto) {
        if (prestataireService.getPrestataire(id) != null)
            return new ResponseEntity<>(prestataireService.updatePrestataire(id, prestataireDto), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/update-nom")
    @Operation(summary = "update prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " updating of the name's prestataire"),
            @ApiResponse(responseCode = "404", description = "the prestataire doesn't exisits")
    })
     public ResponseEntity<PrestataireDto> updatePrestataireName(@Parameter(description = "id of prestTaire") @PathVariable UUID id , @RequestBody PrestataireDto prestataireDto) {
        if (prestataireService.getPrestataire(id) != null)
            return new ResponseEntity<>(prestataireService.updatePrestataireName(id, prestataireDto), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/update-mail")
    @Operation(description = "update Adresse mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = " updating of the mail's prestataire"),
            @ApiResponse(responseCode = "404", description = "the prestataire doesn't exisits")
    })
    public ResponseEntity<PrestataireDto> updatePrestataireAdrresse(@Parameter(description = "id of prestTaire") @PathVariable UUID id , @RequestBody PrestataireDto prestataireDto) {
        if (prestataireService.getPrestataire(id) != null)
            return new ResponseEntity<>(prestataireService.updatePrestataireMail(id, prestataireDto), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/update-position")
    @Operation(summary = "update position of prestaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "updating of position of prestataire"),
            @ApiResponse(responseCode = "404", description = "the prestataire doesn't exisits")
    })
    public ResponseEntity<PrestataireDto> updatePrestatairePosition(@Parameter(description = "id of prestTaire") @PathVariable UUID id , @RequestBody PrestataireDto prestataireDto) {
        if (prestataireService.getPrestataire(id) != null)
            return new ResponseEntity<>(prestataireService.updatePrestataireCoordonnees(id, prestataireDto), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PatchMapping("/{id}/update-addresse")
    @Operation(summary = "change adresse of restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "The updating is okay"),
            @ApiResponse(responseCode = "400",description = "Bad entry is json")
    })
    public ResponseEntity<PrestataireDto> updateAdress(@Parameter(description = "Id Of Prestataire")@PathVariable UUID id,@RequestBody PrestataireDto prestataireDto){
        if (prestataireService.getPrestataire(id) != null)
            return new ResponseEntity<>(prestataireService.updateAdresse(id, prestataireDto), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "delete prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "deleting of position of prestataire is success"),
            @ApiResponse(responseCode = "404", description = "the prestataire doesn't exisits")
    })
    public ResponseEntity<String> deletePrestataire(@Parameter(description = "id of prestataire") @PathVariable UUID id){
        if(prestataireService.deletePrestataire(id))
            return new ResponseEntity<>("{\"message\" : \"prestataire is deleted successfully\"}",HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"message\" : \"prestataire doesn't exists\"}",HttpStatus.NOT_FOUND);
    }
}
