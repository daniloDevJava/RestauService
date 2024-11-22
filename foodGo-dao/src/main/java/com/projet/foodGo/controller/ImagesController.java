package com.projet.foodGo.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.UUID;
import com.projet.foodGo.dto.ImagesDto;
import com.projet.foodGo.service.ImageService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/dao/images")
@RequiredArgsConstructor
public class ImagesController {
    private final ImageService imageService;

    @PostMapping("/upload")
        @Operation(summary = "upload images of an product")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201",description = "the image is uploaded successfully"),
                @ApiResponse(responseCode = "500",description = "product is not found or input of file image is not good ")
        })
        public ResponseEntity<ImagesDto> createImage(@RequestParam("file")MultipartFile file,@Parameter(description = "the id of product") @RequestParam("productId")UUID productId){
            try{
                ImagesDto imagesDto= imageService.createImage(productId,file);
                return new ResponseEntity<>(imagesDto,HttpStatus.CREATED);
            }
            catch (IOException e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            catch (IllegalArgumentException e){
                System.err.println(e.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    @GetMapping("/all-of-product")
    @Operation(summary = "get all images of one product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "the found list"),
            @ApiResponse(responseCode = "500",description = "Not images available of the product specificated")
    })
    public ResponseEntity<List<ImagesDto>> getAll(@Parameter(description = "Id od Product") @RequestParam("productId") UUID productId){
        try {
            return new ResponseEntity<>(imageService.getAll(productId),HttpStatus.OK);
        }
        catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "get an image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "The found image"),
            @ApiResponse(responseCode = "404",description = "The image asked doen't exists")
    })
    public ResponseEntity<ImagesDto> getImage(@Parameter(description = "The Id of Image") @PathVariable("id") Long id){
        ImagesDto imagesDto= imageService.getImage(id);
        if(imagesDto != null)
            return new ResponseEntity<>(imagesDto,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{nom}/nameImg")
    @Operation(summary = "get an image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "The found image"),
            @ApiResponse(responseCode = "500",description = "The image is not for this user"),
            @ApiResponse(responseCode = "404",description = "The image asked doen't exists")
    })
    public ResponseEntity<ImagesDto> getImage(@Parameter(description = "Id of Product") @RequestParam("productId") UUID productId,@Parameter(description = "name of Image") @PathVariable("nom") String nom){
        ImagesDto imagesDto= imageService.getImage(nom,productId);
        if(imagesDto != null)
            return new ResponseEntity<>(imagesDto,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updating image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "everything is up-to-date"),
            @ApiResponse(responseCode = "500",description = "check the name , this action is not authorized"),
            @ApiResponse(responseCode = "404",description = "Not image found")
    }
    )
    public ResponseEntity<ImagesDto> updateImage(@Parameter(description = "Id of Image") @PathVariable("id") Long id, @RequestBody ImagesDto imagesDto,@RequestParam("file") MultipartFile file)
    {
        try {
            ImagesDto images= imageService.updateImage(id,imagesDto,file);
            if(imagesDto!=null)
                return new ResponseEntity<>(images,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/update-name")
    @Operation(summary = "Updating image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "everything is up-to-date"),
            @ApiResponse(responseCode = "500",description = "check the name , this action is not authorized"),
            @ApiResponse(responseCode = "404",description = "Not image found")
    }
    )
    public ResponseEntity<ImagesDto> updateImage(@Parameter(description = "Id of Image") @PathVariable("id") Long id,  @RequestBody ImagesDto imagesDto){
        try {
            ImagesDto images= imageService.updateName(id,imagesDto);
            if(images!=null)
                return new ResponseEntity<>(images,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleting of an image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "deleting of image is okay"),
            @ApiResponse(responseCode = "404",description = "the image is not found")
    })
    public ResponseEntity<String> deletePrestataire(@Parameter(description = "id of prestataire") @PathVariable Long id){
        if(imageService.deleteImage(id))
            return new ResponseEntity<>("{\"message\" : \"image is deleted successfully\"}",HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"message\" : \"image doesn't exists\"}",HttpStatus.NOT_FOUND);
    }









}
