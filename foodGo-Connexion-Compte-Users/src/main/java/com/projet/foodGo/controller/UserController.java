package com.projet.foodGo.controller;

import com.projet.foodGo.exceptions.BusinessException;
import com.projet.foodGo.external.RegisterRequest;
import com.projet.foodGo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-management")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/register")
    @Operation(summary="register an user")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201",description = "L'utilisateur est en attente de validation"),
            @ApiResponse(responseCode = "400",description = "Exceptions levée par le client ")

    })
    public ResponseEntity<RegisterRequest> addUser(@RequestBody RegisterRequest userDto) throws BusinessException {
            RegisterRequest user=userService.registerUser(userDto);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

    }
}
