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
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    public final UserService userService;

    @PostMapping("/add")
    @Operation(description = "add a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success"),
            @ApiResponse(responseCode = "400",description = "un champ oublie,un en trop ...")
    })
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto user=userService.createUser(userDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Operation(description = "get all users")
    @ApiResponse(responseCode = "200",description = "the all list found")
    public ResponseEntity<List<UserDto>> getUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    @Operation(description = "get an user and details")
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
    @Operation(description = "update user")
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

    @DeleteMapping("/{id}")
    @Operation(description = "delete user")
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
