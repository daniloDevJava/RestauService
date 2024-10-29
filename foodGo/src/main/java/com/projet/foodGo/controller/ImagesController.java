package com.projet.foodGo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
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
    public Mono<ResponseEntity<ImagesDto>> createImage(@RequestPart("file") FilePart file, @RequestParam("productId") UUID productId) {
        return imageService.createImage(productId, file)
                .map(imagesDto -> ResponseEntity.status(HttpStatus.CREATED).body(imagesDto))
                .onErrorResume(IOException.class, e -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                })
                .onErrorResume(IllegalArgumentException.class, e -> {
                    System.err.println(e.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
                });
    }
}
