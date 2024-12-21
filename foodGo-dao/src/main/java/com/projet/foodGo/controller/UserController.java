package com.projet.foodGo.controller;

import com.projet.foodGo.dto.UserDto;
import com.projet.foodGo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dao/users")
@AllArgsConstructor
public class UserController {

    public final UserService userService;

    @PostMapping("/add")
    @Operation(summary = "add a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success"),
            @ApiResponse(responseCode = "400",description = "un champ oublie,un en trop ...")
    })
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto user=userService.createUser(userDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/update-montant")
    @Operation(summary="Update Montant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the updating is ok"),
            @ApiResponse(responseCode = "400",description = "pas de montant négatif")
    })
    public ResponseEntity<UserDto> updateMontantCompte(@Parameter(description = "Id of user") @PathVariable UUID id,@RequestBody UserDto userDto){
        UserDto user=userService.updateMontantCompte(id,userDto);
        if(user!=null)
            return new ResponseEntity<>(user,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @Operation(summary = "get all users")
    @ApiResponse(responseCode = "200",description = "the all list found")
    public ResponseEntity<List<UserDto>> getUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    @Operation(summary = "get an user and details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found user"),
            @ApiResponse(responseCode = "404",description = "not user with this id")
    })
    public ResponseEntity<UserDto> getUsers(@Parameter(description = "Id of user") @PathVariable UUID user_id){
        UserDto userDto= userService.getUser(user_id);
        if(userDto!=null)
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{user_id}")
    @Operation(summary = "update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "this user is up-to-date"),
            @ApiResponse(responseCode = "404",description = "this user is not found")
    })
    public ResponseEntity<UserDto> updateUser(@Parameter(description = "the id of user") @PathVariable UUID user_id,@RequestBody UserDto userDto )
    {
        if(userService.getUser(user_id)!=null)
            return new ResponseEntity<>(userService.updateUser(user_id,userDto),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/update-password")
    @Operation(summary = "partial updating")
    @ApiResponses(
            value={
                    @ApiResponse(responseCode = "200",description = "the password is up-to-date"),
                    @ApiResponse(responseCode = "404",description = "the user doen't exists"),
                    @ApiResponse(responseCode = "500",description = "the old password is not correct")
            }
    )
    public ResponseEntity<String> updatePassWord(@Parameter(description = "id of user") @PathVariable UUID id,@RequestBody UserDto userDto,@Parameter(description = "The old password") @RequestParam String oldPassWord){
        try{
            UserDto userDto1=userService.getUser(id);
            if(userDto1!=null) {
                userService.updateUserPassWord(id,userDto,oldPassWord);
                return new ResponseEntity<>("{\"message\" : \"Mot de passe mis a jour avec succes\"", HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IllegalArgumentException e){
            System.err.printf("%s",e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the deleting was successfully"),
            @ApiResponse(responseCode = "404",description = "the user is not found")
            }
    )
    public ResponseEntity<String> deleteUser(@Parameter(description = "id of user") @PathVariable UUID id){
        if(userService.deleteUser(id))
            return new ResponseEntity<>("{\"message\" : \"user is deleted successfully\"}",HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"message\" : \"user doesn't exists\"}",HttpStatus.NOT_FOUND);
    }


}
