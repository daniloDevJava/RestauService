package com.projet.foodGo.controller;

import com.projet.foodGo.dto.AdminDto;
import com.projet.foodGo.service.AdminService;
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
@RequestMapping("/dao/admins")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/add")
    @Operation(summary = "add a admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "success"),
            @ApiResponse(responseCode = "400",description = "A field is missing"),
            @ApiResponse(responseCode = "500",description = "admin unauthorized")
    })
    public ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto){
        AdminDto admin=adminService.createAdmin(adminDto);
        if(admin!=null)
            return new ResponseEntity<>(admin, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found admin"),
            @ApiResponse(responseCode = "404",description = "Admin is not found")
    })
    public ResponseEntity<AdminDto> getAdmin(@Parameter(description = "Id of Admin") @PathVariable UUID id){
        AdminDto adminDto= adminService.getAdmin(id);
        if(adminDto!=null)
            return new ResponseEntity<>(adminDto,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{nom}/get-by-name")
    @Operation(summary = "Get Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found admin"),
            @ApiResponse(responseCode = "404",description = "Admin is not found")
    })
    public ResponseEntity<AdminDto> getAdmin(@Parameter(description = "Name of Admin") @PathVariable String nom){
        AdminDto adminDto= adminService.getAdmin(nom);
        if(adminDto!=null)
            return new ResponseEntity<>(adminDto,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @Operation(summary = "Get admins")
    @ApiResponse(responseCode = "200",description = "The list of")
    public ResponseEntity<List<AdminDto>> getAdmins(){
        return new ResponseEntity<>(adminService.getAll(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "full updating of an admim")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the updating is okay"),
            @ApiResponse(responseCode = "404",description = "admin doesn't exist")
    })
    public ResponseEntity<AdminDto> updateAdmin(@Parameter(description = "Id of Admin") @PathVariable UUID id, AdminDto adminDto){
        AdminDto admin= adminService.getAdmin(id);
        if(admin!=null)
            return new ResponseEntity<>(adminService.updateAdmin(id,adminDto),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}/update-entryKey")
    @Operation(summary = "partial updating of an admim")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the updating of entryKey is okay"),
            @ApiResponse(responseCode = "404",description = "admin doesn't exist"),
            @ApiResponse(responseCode ="401",description = "Cet admin n'a pas le droit de changer la clé d'entrée C'est le premier dans la liste des admins qui en a le droit veuillez consulter celle-ci et le contacter(L'admin)")
    })
    public ResponseEntity<AdminDto> updateEntryKey(@Parameter(description = "Id Of Admin") @PathVariable UUID id,@RequestBody AdminDto adminDto){
        //AdminDto adminDto1=adminService.getAdmin(id);
        AdminDto admin=adminService.updateEntryKey(id,adminDto);
        if(admin!=null && admin.getEntryKey().equals(adminDto.getEntryKey()))
            return new ResponseEntity<>(admin,HttpStatus.OK);
        else if (admin != null)
            return new ResponseEntity<>(admin,HttpStatus.UNAUTHORIZED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete admin")
    public ResponseEntity<String> deleteAdmin(@Parameter(description = "Id Of Admin") @PathVariable UUID id){
        if(adminService.deleteAdmin(id))
            return new ResponseEntity<>("{\"message\" : \"admin is deleted successfully\"}",HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"message\" : \"admin doesn't exists\"}",HttpStatus.NOT_FOUND);
    }

}
