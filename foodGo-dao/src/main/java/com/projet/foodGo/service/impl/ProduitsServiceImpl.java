package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.ProduitsDto;
import com.projet.foodGo.exeptions.BusinessException;
import com.projet.foodGo.exeptions.ErrorModel;
import com.projet.foodGo.mapper.ProduitsConverter;
import com.projet.foodGo.model.Images;
import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.repository.ImagesRepository;
import com.projet.foodGo.repository.PrestataireRepository;
import com.projet.foodGo.repository.ProduitsRepository;
import com.projet.foodGo.service.ProduitsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProduitsServiceImpl implements ProduitsService {

    private final ProduitsConverter produitsConverter;
    private final ProduitsRepository produitsRepository;
    private final PrestataireRepository prestataireRepository;
    private final ImagesRepository imagesRepository;

    @Override
    public ProduitsDto createProduct(ProduitsDto produitDto, UUID prestataire_id) {
        Optional<Prestataire> optionalprestataire=prestataireRepository.findByIdAndDeleteAtIsNull(prestataire_id);
        if(optionalprestataire.isPresent()){
            Prestataire prestataire= optionalprestataire.get();
            Optional<Produits> optionalProduits=produitsRepository.findByPrestataireOrderByLibelle(prestataire_id, produitDto.getLibelle());
            if(optionalProduits.isEmpty()) {
                Produits produits = produitsConverter.toEntity(produitDto);
                produits.setPrestataire(prestataire);
                produits=produitsRepository.save(produits);
                return produitsConverter.toDto(produits);
            }
            else {
            	List<ErrorModel> errorModels=new ArrayList<>();
            	ErrorModel errorModel=new ErrorModel();
            	errorModel.setCode("INVALID_ENTRY");
            	errorModel.setMessage("Impossible d'entrer un meme libelle pour deux produits veuillez modifier la quantite en stocke et/ou d'autres caracteristiques du produit");
            	errorModels.add(errorModel); 
                throw new BusinessException(errorModels);
                }
        }
        else
            return null;
    }
    @Transactional(readOnly = true)
    @Override
    public List<ProduitsDto> getProducts(UUID prestataire_id) {
        Optional<Prestataire> optionalprestataire=prestataireRepository.findByIdAndDeleteAtIsNull(prestataire_id);
        if(optionalprestataire.isPresent()) {
            Prestataire prestataire = optionalprestataire.get();
            List<Produits> produitsList = produitsRepository.findByPrestataireAndDeleteAtIsNull(prestataire);
            return produitsList.stream()
                    .map(produitsConverter::toDto)
                    .collect(Collectors.toList());
        }
        else
            throw new IllegalArgumentException("Prestataire non trouvé");
    }

    /**
     * @return 
     */
    @Transactional(readOnly = true)
    @Override
    public List<ProduitsDto> getProducts() {
        List<Produits> produitsList=produitsRepository.findByDeleteAtIsNull();
        return produitsList.stream()
                .map(produitsConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @param id 
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public ProduitsDto getProduct(UUID id) {
        Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(id);
        return optionalProduits.map(produitsConverter::toDto).orElse(null);
    }

    /**
     * @param nom
     * @param prestataire_id
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public ProduitsDto getProduct(String nom, UUID prestataire_id) {
        Optional<Prestataire> optionalprestataire=prestataireRepository.findByIdAndDeleteAtIsNull(prestataire_id);
        if(optionalprestataire.isPresent()) {
            Prestataire prestataire = optionalprestataire.get();
            Optional<Produits> optionalProduits = produitsRepository.findByPrestataireOrderByLibelle(prestataire_id, nom);
            if(optionalProduits.isPresent()){
                return produitsConverter.toDto(optionalProduits.get());
            }
            else
                return null;
        }
        else 
            throw new IllegalArgumentException("Prestataire non trouvé");
    }

    /**
     * @param id 
     * @param produitAlimentaireDto
     * @return
     */
    @Override
    public ProduitsDto updateProduct(UUID id, ProduitsDto produitAlimentaireDto) {
        Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(id);
        if (optionalProduits.isPresent())
        {
            List<Images> imagesList=new ArrayList<>();
            Produits produits= optionalProduits.get();
            produits.setLibelle(produitAlimentaireDto.getLibelle());
            produits.setQuantiteStock(produitAlimentaireDto.getQuantiteStock());
            if(produitAlimentaireDto!=null) {
                for (Long idImage : produitAlimentaireDto.getImagesList()) {
                    Optional<Images> optionalImages = imagesRepository.findByIdAndDeleteAtIsNull(idImage);
                    optionalImages.ifPresent(imagesList::add);
                }
            }
            produits.setImagesList(imagesList);
            produits.setPrixUnitaire(produitAlimentaireDto.getPrixUnitaire());
            return produitsConverter.toDto(produitsRepository.save(produits));
        }
        else 
            return null;
    }

    /**
     * @param prestataire_id 
     * @return
     */
    @Override
    public List<ProduitsDto> getCorbeille(UUID prestataire_id) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(prestataire_id);
        if(optionalPrestataire.isPresent())
        {
            List<Produits> produitsList=produitsRepository.findByPrestataireAndDeleteAtIsNotNull(optionalPrestataire.get());
            return produitsList.stream()
                    .map(produitsConverter::toDto)
                    .collect(Collectors.toList());
        }
        else
            throw new IllegalArgumentException("Prestataire non trouvé");
    }

    /**
     * @param produit_id 
     * @param produitsDto
     * @return
     */
    @Override
    public ProduitsDto updateQuantiteStock(UUID produit_id, ProduitsDto produitsDto) {
        Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(produit_id);
        if(optionalProduits.isPresent()){
            Produits produits= optionalProduits.get();
            produits.setQuantiteStock(produitsDto.getQuantiteStock());
            return produitsConverter.toDto(produitsRepository.save(produits));
        }
        else
            return null;
    }

    /**
     * @param prestataire
     */
    @Override
    public void markAsProductDeleted(Prestataire prestataire) {
        List<Produits> produitsList=produitsRepository.findByPrestataireAndDeleteAtIsNull(prestataire);
        for(Produits produits:produitsList){
            produits.setDeleteAt(LocalDateTime.now());
            produitsRepository.save(produits);
        }

    }

    /**
     * @param id 
     * @return
     */
    @Override
    public boolean deleteProduct(UUID id) {
        Optional<Produits> optionalProduits=produitsRepository.findByIdAndDeleteAtIsNull(id);
        if (optionalProduits.isPresent()) {
            Produits produits = optionalProduits.get();
            produits.setDeleteAt(LocalDateTime.now());
            produitsRepository.save(produits);
            return true;
        }
        return false;
    }
}
