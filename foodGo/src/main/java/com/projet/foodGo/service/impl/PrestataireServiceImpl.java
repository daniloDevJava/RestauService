package com.projet.foodGo.service.impl;


import com.projet.foodGo.dto.PrestataireDto;
import com.projet.foodGo.mapper.PrestataireConverter;
import com.projet.foodGo.mapper.ProduitsConverter;
import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.repository.PrestataireRepository;
import com.projet.foodGo.service.PrestataireService;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PrestataireServiceImpl implements PrestataireService {
    private final PrestataireRepository prestataireRepository;
    private final PrestataireConverter prestataireConverter;
    private final ProduitsConverter produitConverter;

    /**
     * @param prestataireDto 
     * @return
     */
    @Override
    public PrestataireDto createPrestataire(PrestataireDto prestataireDto) {
        Prestataire prestataire=prestataireConverter.toEntity(prestataireDto);
        return prestataireConverter.toDto(prestataireRepository.save(prestataire));
    }

    /**
     * @return 
     */
    @Override
    public List<PrestataireDto> getPrestataires() {
        List<Prestataire> listPrestataires= prestataireRepository.findByDeleteAtIsNull();
        return listPrestataires.stream()
                .map(prestataireConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @param id 
     * @return
     */
    @Override
    public PrestataireDto getPrestataire(UUID id) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalPrestataire.isPresent()){
            Prestataire prestataire= optionalPrestataire.get();
            return prestataireConverter.toDto(prestataire);
        }
        else 
            return null;
    }

    /**
     * @param nom 
     * @return
     */
    @Override
    public PrestataireDto getPrestataire(String nom) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByNomAndDeleteAtIsNull(nom);
        if(optionalPrestataire.isPresent()){
            Prestataire prestataire= optionalPrestataire.get();
            return prestataireConverter.toDto(prestataire);
        }
        else 
            return null;
    }

    /**
     * @param id 
     * @param prestataireDto
     * @return
     */
    @Override
    public PrestataireDto updatePrestataire(UUID id, PrestataireDto prestataireDto) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalPrestataire.isPresent()) {
            Point geography=new Point(prestataireDto.getLongitude(), prestataireDto.getLatitude());
            Prestataire prestataire = optionalPrestataire.get();
            prestataire.setAdresseMail(prestataireDto.getAdresseMail());
            prestataire.setNatureCompte(prestataireDto.getNatureCompte());
            prestataire.setNom(prestataireDto.getNom());
            prestataire.setListProduits(produitConverter.toEntityList(prestataireDto.getListProduits()));
            prestataire.setGeography(geography);
            return prestataireConverter.toDto(prestataire);
        }
        else
            return null;
    }

    /**
     * @param id 
     * @param prestataireDto
     * @return
     */
    @Override
    public PrestataireDto updatePrestataireName(UUID id, PrestataireDto prestataireDto) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalPrestataire.isPresent()) {
            Prestataire prestataire = optionalPrestataire.get();
            prestataire.setNom(prestataireDto.getNom());
            return prestataireConverter.toDto(prestataireRepository.save(prestataire));
        }
        else
            return null;
    }

    /**
     * @param id 
     * @param prestataireDto
     * @return
     */
    @Override
    public PrestataireDto updatePrestataireMail(UUID id, PrestataireDto prestataireDto) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalPrestataire.isPresent()) {
            Prestataire prestataire = optionalPrestataire.get();
            prestataire.setAdresseMail(prestataireDto.getAdresseMail());
            return prestataireConverter.toDto(prestataireRepository.save(prestataire));
        }
        else
            return null;
    }

    /**
     * @param id 
     * @param prestataireDto
     * @return
     */
    @Override
    public PrestataireDto updatePrestataireCoordonnees(UUID id, PrestataireDto prestataireDto) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalPrestataire.isPresent()) {
            Prestataire prestataire = optionalPrestataire.get();
            Point geography=new Point(prestataireDto.getLongitude(), prestataireDto.getLatitude());
            prestataire.setGeography(geography);
            return prestataireConverter.toDto(prestataireRepository.save(prestataire));
        }
        else
            return null;
    }

    /**
     * @param id 
     * @param prestataireDto
     * @return
     */
  /*  @Override
    public PrestataireDto updateListProduits(UUID id, PrestataireDto prestataireDto) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalPrestataire.isPresent()) {
            Prestataire prestataire = optionalPrestataire.get();
            prestataire.setListProduits(produitConverter.toEntityList(prestataireDto.getListProduits()));
            return prestataireConverter.toDto(prestataireRepository.save(prestataire));
        }
        else
            return null;
    }*/

    /**
     * @param id 
     * @return
     */
    @Override
    public boolean deletePrestataire(UUID id) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalPrestataire.isPresent()){
            Prestataire prestataire= optionalPrestataire.get();
            prestataire.setDeleteAt(LocalDateTime.now());
            prestataireRepository.save(prestataire);
            return true;
        }
        else 
            return false;
    }
}
