package com.projet.foodGo.service.impl;


import com.projet.foodGo.dto.PrestataireDto;
import com.projet.foodGo.exeptions.BusinessException;
import com.projet.foodGo.exeptions.ErrorModel;
import com.projet.foodGo.mapper.PrestataireConverter;
import com.projet.foodGo.model.Prestataire;
import com.projet.foodGo.model.Produits;
import com.projet.foodGo.model.enumType.RoleUser;
import com.projet.foodGo.repository.PrestataireRepository;
import com.projet.foodGo.repository.ProduitsRepository;
import com.projet.foodGo.service.PrestataireService;
import com.projet.foodGo.service.ProduitsService;
import lombok.AllArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Prestataire service.
 */
@Service
@AllArgsConstructor
public class PrestataireServiceImpl implements PrestataireService {
    private final PrestataireRepository prestataireRepository;
    private final PrestataireConverter prestataireConverter;
    private final ProduitsRepository produitsRepository;
    private final ProduitsService produitsService;

    @Override
    public PrestataireDto createPrestataire(PrestataireDto prestataireDto) {
        prestataireDto.setNoteMoyenne(0.0);
        prestataireDto.setListProduits(new ArrayList<>());
        prestataireDto.setRole(RoleUser.VENDEUR);
        prestataireDto.setMontantCompte(0.0);
        Optional<Prestataire> optionalPrestataire= prestataireRepository.findByNomAndDeleteAtIsNull(prestataireDto.getNom());
        if(optionalPrestataire.isEmpty()) {
            Prestataire prestataire = prestataireConverter.toEntity(prestataireDto);
            return prestataireConverter.toDto(prestataireRepository.save(prestataire));
        }
        else
        {
            List<ErrorModel> errorModelList=new ArrayList<>();
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("OPERATION_DENIED");
            errorModel.setMessage("Ce nom est réservé");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<PrestataireDto> getPrestataires() {
        List<Prestataire> listPrestataires= prestataireRepository.findByDeleteAtIsNull();
        return listPrestataires.stream()
                .map(prestataireConverter::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    //@Transactional(readOnly = true)
    @Override
    public PrestataireDto updatePrestataire(UUID id, PrestataireDto prestataireDto) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalPrestataire.isPresent()) {
            Point geography=new Point(prestataireDto.getLongitude(), prestataireDto.getLatitude());
            List<Produits> listProduits=new ArrayList<>();
            Prestataire prestataire = optionalPrestataire.get();
            prestataire.setNom(prestataireDto.getNom());
            prestataire.setAdresseMail(prestataireDto.getAdresseMail());
            prestataire.setAdresse(prestataireDto.getAddress());
            prestataire.setNatureCompte(prestataireDto.getNatureCompte());
            prestataire.setMotDePasse(prestataireDto.getMotDePasse());
            if(prestataireDto.getListProduits()!=null) {
                for (UUID produit_id : prestataireDto.getListProduits()) {
                    Optional<Produits> optionalProduits = produitsRepository.findByIdAndDeleteAtIsNull(produit_id);
                    optionalProduits.ifPresent(listProduits::add);
                }
            }
            prestataire.setListProduits(listProduits);
            prestataire.setGeography(geography);
            prestataire=prestataireRepository.save(prestataire);
            return prestataireConverter.toDto(prestataireRepository.save(prestataire));
        }
        else
            return null;
    }

    // @Transactional(readOnly = true)
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

    //@Transactional(readOnly = true)
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

    //@Transactional(readOnly = true)
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


    @Override
    public boolean deletePrestataire(UUID id) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalPrestataire.isPresent()){
            Prestataire prestataire= optionalPrestataire.get();
            prestataire.setDeleteAt(LocalDateTime.now());
            prestataireRepository.save(prestataire);
            produitsService.markAsProductDeleted(prestataire);
            return true;
        }
        else 
            return false;
    }


    @Override
    public PrestataireDto updateAdresse(UUID prestataireId, PrestataireDto prestataireDto) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByIdAndDeleteAtIsNull(prestataireId);
        if(optionalPrestataire.isPresent()) {
            Prestataire prestataire= optionalPrestataire.get();
            prestataire.setAdresse(prestataireDto.getAddress());
            return prestataireConverter.toDto(prestataireRepository.save(prestataire));
        }
        else
            return null;
    }

    /**
     * @param libelle
     * @return
     */
    @Override
    public List<PrestataireDto> getPrestatairesByFood(String libelle) {
        List<Prestataire> prestataires=prestataireRepository.findPrestatairesByProduitLibelle(libelle);

        return prestataires.stream()
                .map(prestataireConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @param mail 
     * @param prestataireDto
     * @return
     */
    @Override
    public PrestataireDto updatePrestataireNature(String mail, PrestataireDto prestataireDto) {
        Optional<Prestataire> optionalPrestataire=prestataireRepository.findByAdresseMailAndDeleteAtIsNull(mail);
        if(optionalPrestataire.isPresent()){
            Prestataire prestataire= optionalPrestataire.get();
            prestataire.setNatureCompte(prestataireDto.getNatureCompte());
            return prestataireConverter.toDto(prestataireRepository.save(prestataire));
        }
        else{
            List<ErrorModel> errorModelList=new ArrayList<>();
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("NOT_FOUND");
            errorModel.setMessage("l'adresse mail est incorrecte");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
    }
}
