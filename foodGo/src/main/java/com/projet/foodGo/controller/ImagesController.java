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
@RequestMapping("/images")
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



}
