package com.projet.foodGo.controller;

import com.projet.foodGo.dto.ClientDto;
import com.projet.foodGo.service.ClientService;
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
@RequestMapping("/dao/clients")
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/add")
    @Operation(summary = "add an client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "new client now"),
            @ApiResponse(responseCode = "400",description = "a field of client is missing")
    })
    public ResponseEntity<ClientDto> createClient( @RequestBody ClientDto clientDto){
        return new ResponseEntity<>(clientService.createClient(clientDto), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Operation(summary = "get all clients")
    @ApiResponse(responseCode = "200",description = "the all list of clients")
    public ResponseEntity<List<ClientDto>> getClients(){
        return new ResponseEntity<>(clientService.getClients(),HttpStatus.OK);
    }
    @GetMapping("/{client_id}")
    @Operation(summary = "get an client by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found client"),
            @ApiResponse(responseCode = "404",description = "client doen't exists")
    })
    public ResponseEntity<ClientDto> getClient(@Parameter(description = "the client id") @PathVariable UUID client_id){
        ClientDto clientDto= clientService.getClient(client_id);
        if(clientDto!=null)
            return new ResponseEntity<>(clientDto,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{nom}/getByName")
    @Operation(summary = "get client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found client"),
            @ApiResponse(responseCode = "404",description = "client doen't exists")
    })
    public ResponseEntity<ClientDto> getClient(@Parameter(description = "the full name of client") @PathVariable String nom){
        ClientDto clientDto= clientService.getClient(nom);
        if(clientDto!=null)
            return new ResponseEntity<>(clientDto,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{client_id}")
    @Operation(summary = "full updating Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found new client"),
            @ApiResponse(responseCode = "404",description = "client doen't exists")
    })
    public ResponseEntity<ClientDto> updateClient(@Parameter(description = "id of client") @PathVariable UUID client_id,@Valid @RequestBody ClientDto clientDto){
        if(clientService.getClient(client_id)!=null)
            return new ResponseEntity<>(clientService.updateClient(clientDto,client_id),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PatchMapping("/{id}/update-addr")
    @Operation(summary = "partial updating Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found new client"),
            @ApiResponse(responseCode = "404",description = "client doen't exists")
    })
    public ResponseEntity<ClientDto> updateClientAddr(@Parameter(description = "id of client") @PathVariable UUID id,@Valid @RequestBody ClientDto clientDto){
        if(clientService.getClient(id)!=null)
            return new ResponseEntity<>(clientService.updateAdresse(id,clientDto),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/update-mail")
    @Operation(summary = "partial updating Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found new client"),
            @ApiResponse(responseCode = "404",description = "client doen't exists")
    })
    public ResponseEntity<ClientDto> updateClientMail(@Parameter(description = "id of client") @PathVariable UUID id,@Valid @RequestBody ClientDto clientDto){
        if(clientService.getClient(id)!=null)
            return new ResponseEntity<>(clientService.updateAdresseMail(id,clientDto),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/update-dateOfBirth")
    @Operation(summary = "partial updating Client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found client"),
            @ApiResponse(responseCode = "404",description = "client doen't exists")
    })
    public ResponseEntity<ClientDto> updateClientDateOfBirth(@Parameter(description = "id of client") @PathVariable UUID id,@Valid @RequestBody ClientDto clientDto){
        if(clientService.getClient(id)!=null)
            return new ResponseEntity<>(clientService.updateDateOfBirth(id,clientDto),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the deleting was successfully"),
            @ApiResponse(responseCode = "404",description = "the client is not found")
    }
    )    public ResponseEntity<String> deleteClient(@Parameter(description = "id of client") @PathVariable UUID id){
        if(clientService.deleteClient(id))
            return new ResponseEntity<>("{\"message\" : \"client is deleted successfully\"}",HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"message\" : \"client doesn't exists\"}",HttpStatus.NOT_FOUND);
    }



}
