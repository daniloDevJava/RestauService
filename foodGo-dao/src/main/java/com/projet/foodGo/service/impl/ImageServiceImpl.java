package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.ImagesDto;
import com.projet.foodGo.mapper.ImageConverter;
import com.projet.foodGo.model.*;
import com.projet.foodGo.repository.ImagesRepository;
import com.projet.foodGo.repository.ProduitsRepository;
import com.projet.foodGo.service.ImageService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageConverter imageConverter;
    private final ProduitsRepository produitsRepository;
    private final ImagesRepository imagesRepository;

    @Override
    public ImagesDto createImage(UUID product_id, MultipartFile file) throws IOException {
        Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(product_id);
        if(optionalProduits.isPresent()){
            Images images=new Images();
            Produits produits= optionalProduits.get();
            // Créer le dossier si non existant
            String imageDirectory = "images/produits/";
            Path directoryPath = Paths.get(imageDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            // Créer le chemin du fichier
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            Path filePath = directoryPath.resolve(fileName);
            images.setChemin(fileName);
            images.setProduit(produits);
            images=imagesRepository.save(images);
            images.makeName();


            // Sauvegarder le fichier
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return imageConverter.toDto(imagesRepository.save(images));

        }
        else
            throw new IllegalArgumentException("Image non trouve");
    }

    @Override
    public boolean deleteImage(Long image_id) {
        Optional<Images> optionalImages=imagesRepository.findByIdAndDeleteAtIsNull(image_id);
        if(optionalImages.isPresent()){
            Images images= optionalImages.get();
            images.setDeleteAt(LocalDateTime.now());
            imagesRepository.save(images);
            return true;
        }
        else
            return false;
    }

    @Override
    public ImagesDto getImage(Long imageId) {
        Optional<Images> optionalImages=imagesRepository.findByIdAndDeleteAtIsNull(imageId);
        return optionalImages.map(imageConverter::toDto).orElse(null);
    }

    @Override
    public ImagesDto updateName(Long imageId, ImagesDto imagesDto) {
        Optional<Images> optionalImages=imagesRepository.findByIdAndDeleteAtIsNull(imageId);
        Optional<Images> imagesOptional=imagesRepository.findByNomAndDeleteAtIsNull(imagesDto.getNom());
        if(optionalImages.isPresent()) {
            Images images = optionalImages.get();
            if (imagesOptional.isEmpty()) {
                images.setNom(imagesDto.getNom());
                return imageConverter.toDto(imagesRepository.save(images));
            }
            else
                throw new IllegalArgumentException("Nom réservé veuillez entrer un autre nom");
        }
        else
            return null;
    }

    @Override
    public ImagesDto getImage(String nom, UUID productId) {
        Optional<Images> optionalImages=imagesRepository.findByNomAndDeleteAtIsNull(nom);
        Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(productId);
        if(optionalProduits.isPresent() && optionalImages.isPresent()){
            Produits produits= optionalProduits.get();
            List<Images> imagesList=imagesRepository.findByProduitAndDeleteAtIsNull(produits);
            if(imagesList.contains(optionalImages.get()))
                return optionalImages.map(imageConverter::toDto).get();
            else 
                throw  new IllegalArgumentException("Image non autorisé");
                
        }
        else 
            return null;
        
        
    }


    @Override
    public ImagesDto updateImage(Long imageId, ImagesDto imagesDto, MultipartFile file) throws IOException {
        Optional<Images> optionalImages=imagesRepository.findByIdAndDeleteAtIsNull(imageId);
        Optional<Images> imagesOptional=imagesRepository.findByNomAndDeleteAtIsNull(imagesDto.getNom());
        if(optionalImages.isPresent()) {
            Images images = optionalImages.get();
            if(imagesOptional.isEmpty())
                images.setNom(imagesDto.getNom());
            else
                throw new IllegalArgumentException("Nom réservé veuillez entrer un autre nom");
            String imageDirectory = "images/produits/";
            Path directoryPath = Paths.get(imageDirectory);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            // Créer le chemin du fichier
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            Path filePath = directoryPath.resolve(fileName);
            images.setChemin(fileName);
            images=imagesRepository.save(images);


            // Sauvegarder le fichier
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return imageConverter.toDto(imagesRepository.save(images));

        }
        return null;
    }

    @Override
    public List<ImagesDto> getAll(UUID productId) {
        Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(productId);
        if(optionalProduits.isPresent()){
            Produits produits= optionalProduits.get();
            List<Images> imagesList=imagesRepository.findByProduitAndDeleteAtIsNull(produits);
            return imagesList.stream()
                    .map(imageConverter::toDto)
                    .collect(Collectors.toList());
        }
        else
            throw new IllegalArgumentException("Produit non trouvé");

    }
}
