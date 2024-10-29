package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.ImagesDto;
import com.projet.foodGo.mapper.ImageConverter;
import com.projet.foodGo.model.Images;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.repository.ImagesRepository;
import com.projet.foodGo.repository.ProduitsRepository;
import com.projet.foodGo.service.ImageService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    /**
     * @param product_id 
     * @param imagesDto
     * @return
     */
    private final ImageConverter imageConverter;
    private final ProduitsRepository produitsRepository;
    private final ImagesRepository imagesRepository;

    @Override
public Mono<ImagesDto> createImage(UUID productId, FilePart file) {
    return produitsRepository.findByIdAndDeleteAtIsNull(productId)
        .flatMap(produits -> {
            // Créer le dossier si non existant
            String imageDirectory = "images/produits/";
            Path directoryPath = Paths.get(imageDirectory);
            
            // Vérifier et créer le dossier de manière non-bloquante
            return Mono.fromCallable(() -> {
                if (Files.notExists(directoryPath)) {
                    Files.createDirectories(directoryPath);
                }
                return directoryPath;
            }).subscribeOn(Schedulers.boundedElastic())  // Exécute dans un contexte de thread non-bloquant
              .then(Mono.defer(() -> {
                  // Utiliser un nom de fichier unique
                  String fileName = UUID.randomUUID() + "_" + file.filename();
                  Path filePath = directoryPath.resolve(fileName);
                  
                  // Enregistrer le fichier de manière non-bloquante
                  return file.transferTo(filePath)
                      .thenReturn(filePath.toString())
                      .flatMap(chemin -> {
                          // Créer et sauvegarder l'entité Images
                          Images images = new Images();
                          images.setChemin(chemin);
                          return imagesRepository.save(images);
                      })
                      .map(imageConverter::toDto); // Convertir en DTO
              }));
        });
}

    /**
     * @param image_id 
     * @return
     */
    @Override
    public boolean deleteImage(UUID image_id) {
        return false;
    }

    /**
     * @param product_id 
     * @param imagesDto
     * @return
     */
    @Override
    public ImagesDto updateImage(UUID product_id, ImagesDto imagesDto) {
        return null;
    }

    /**
     * @param image_id 
     * @return
     */
    @Override
    public List<ImagesDto> getAll(UUID image_id) {
        return List.of();
    }
}
