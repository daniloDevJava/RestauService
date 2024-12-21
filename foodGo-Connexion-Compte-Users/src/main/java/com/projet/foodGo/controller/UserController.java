package com.projet.foodGo.controller;

import com.projet.foodGo.dto.*;
import com.projet.foodGo.exceptions.BusinessException;
import com.projet.foodGo.external.PrestataireDto;
import com.projet.foodGo.service.JwtService;
import com.projet.foodGo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-management")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    @PostMapping("/register")
    @Operation(summary="register an user")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201",description = "L'utilisateur est en attente de validation"),
            @ApiResponse(responseCode = "400",description = "Exceptions levée par le client ")

    })
    public ResponseEntity<RegisterRequest> addUser(@Valid @RequestBody RegisterRequest userDto) throws BusinessException {
            RegisterRequest user=userService.registerUser(userDto);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @PostMapping("/validate-code")
    @Operation(summary = "validate a code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "l'utilisateur a été enregistré avec succès"),
            @ApiResponse(responseCode = "400",description = "code ou adresse_mail inconnu")
    })
    public ResponseEntity<RegisterRequest> validate(@RequestBody ValidationDto validation) throws BusinessException {
        RegisterRequest user=userService.validate(validation.getEmail(), validation.getCode());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "connexion par le mail et le mot de passe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "l'utilisateur est authentifié et a accès à son compte"),
            @ApiResponse(responseCode = "404",description = "utilisateur non trouvé")
    })
    public ResponseEntity<AuthentificationDto> loginEmailAndPassWord(@RequestBody LoginDto loginRequest) throws BusinessException {

        AuthentificationDto tokens = jwtService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(tokens);

    }


    @PostMapping("/refresh")
    @Operation(summary = "refresh refresh-token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the token if refresh"),
            @ApiResponse(responseCode = "400",description = "Exception coté clien levée")
    })
    public ResponseEntity<AuthentificationDto> refreshToken(@RequestParam("refreshToken") String refreshToken) throws BusinessException {
        AuthentificationDto tokens=jwtService.refreshTokens(refreshToken);
        return new ResponseEntity<>(tokens,HttpStatus.OK);
    }

    @PostMapping("/refreshAccessToken")
    @Operation(summary = "refresh Access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the new access token"),
            @ApiResponse(responseCode = "400",description = "Exception coté client levée")
    })
    public ResponseEntity<AccessTokenDto> refreshAccessToken(@RequestParam("refreshToken") String refreshToken) throws BusinessException {
        AccessTokenDto accestoken=jwtService.refreshAccessToken(refreshToken);
        return new ResponseEntity<>(accestoken,HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Operation(summary = "déconnexion d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the new access token"),
            @ApiResponse(responseCode = "400",description = "Exception coté client levée")
    })
    public ResponseEntity<String> deconnexion(@RequestParam("refreshToken") String refreshToken) throws BusinessException {
        jwtService.invalidateRefreshToken(refreshToken);
        return new ResponseEntity<>("{\"message\": \"déconnexion réussie\"}",HttpStatus.OK);
    }

    @PatchMapping("/update-password")
    @Operation(summary = "Mise à jour du mot de passe par email")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Mot de passe mis à jour avec succès."),
                    @ApiResponse(responseCode = "404", description = "Utilisateur introuvable."),
                    @ApiResponse(responseCode = "500", description = "Erreur lors de la mise à jour du mot de passe.")
            }
    )
    public ResponseEntity<String> updatePassword(
            @RequestParam String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword)throws BusinessException {
        userService.updatePassword(email, newPassword, oldPassword);
        return ResponseEntity.ok("{\"message\": \"Mot de passe mis à jour avec succès.\"}");
    }
    @PatchMapping("/restrict-prestataire")
    @Operation(summary = "Restreindre un prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Le prestataire a été restreint avec succès."),
            @ApiResponse(responseCode = "404", description = "Utilisateur ou prestataire introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur lors de la restriction du prestataire.")
    })
    public ResponseEntity<String> restrictPrestataire(@RequestParam String emailPrestataire,@RequestParam String mailAdmin) {
        try {
            userService.restrictPrestataire(emailPrestataire,mailAdmin);
            return ResponseEntity.ok("{\"message\": \"Prestataire restreint avec succès.\"}");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/unrestricted-prestataire")
    @Operation(summary = "lever la restriction sur un prestataire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La restriction du prestataire a été levée avec succès."),
            @ApiResponse(responseCode = "404", description = "Utilisateur ou prestataire introuvable."),
            @ApiResponse(responseCode = "500", description = "Erreur lors de la levée de la restriction du prestataire.")
    })
    public ResponseEntity<String> unrestrictedPrestataire(@RequestParam String emailPrestataire, @RequestParam String mailAdmin, @RequestBody PrestataireDto prestataireDto){
        try{
            userService.unrestrictedPrestataire(emailPrestataire,mailAdmin,prestataireDto);
            return ResponseEntity.ok("{\"message\": \"la levée de restriction du Prestataire  est un succès.\"}");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
