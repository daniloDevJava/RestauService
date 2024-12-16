package com.projet.foodGo.controller;

import com.projet.foodGo.dto.AuthentificationDto;
import com.projet.foodGo.dto.LoginDto;
import com.projet.foodGo.dto.ValidationDto;
import com.projet.foodGo.exceptions.BusinessException;
import com.projet.foodGo.dto.RegisterRequest;
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
    @Operation(summary = "refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the token if refresh"),
            @ApiResponse(responseCode = "400",description = "Exception coté clien levée")
    })
    public ResponseEntity<AuthentificationDto> refreshToken(@RequestParam("refreshToken") String refreshToken) throws BusinessException {
        AuthentificationDto tokens=jwtService.refreshAccessToken(refreshToken);
        return new ResponseEntity<>(tokens,HttpStatus.OK);
    }


}
