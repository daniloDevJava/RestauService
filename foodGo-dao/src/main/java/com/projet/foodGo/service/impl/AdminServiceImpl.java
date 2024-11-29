package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.AdminDto;
import com.projet.foodGo.exeptions.BusinessException;
import com.projet.foodGo.exeptions.ErrorModel;
import com.projet.foodGo.mapper.AdminConverter;
import com.projet.foodGo.model.Admin;
import com.projet.foodGo.model.enumType.RoleUser;
import com.projet.foodGo.repository.AdminRepository;
import com.projet.foodGo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Admin service.
 */
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminConverter adminConverter;

    @Override
    public AdminDto createAdmin(AdminDto adminDto) {
        List<Admin> adminList=adminRepository.findByDeleteAtIsNull();
        if(adminList.isEmpty()) {
            adminDto.setEntryKey(UUID.randomUUID());
            adminDto.setRole(RoleUser.ADMIN);
            Admin admin=adminRepository.save(adminConverter.toEntity(adminDto));
            return adminConverter.toDto(admin);
        }
        else if(adminList.getFirst().getEntryKey().equals(adminDto.getEntryKey())){
            Admin admin=adminRepository.save(adminConverter.toEntity(adminDto));
            return adminConverter.toDto(admin);
        }
        else {
            List<ErrorModel> errorModelList=new ArrayList<>();
            ErrorModel errorModel=new ErrorModel();
            errorModel.setCode("AUTHENTIFICATION_FAILED");
            errorModel.setMessage("Vous n'etes pas reconnu comme un admin");
            errorModelList.add(errorModel);
            throw new BusinessException(errorModelList);
        }
    }

    @Override
    public AdminDto getAdmin(UUID id) {
        Optional<Admin> optionalAdmin=adminRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalAdmin.isPresent()){
            Admin admin= optionalAdmin.get();
            return adminConverter.toDto(admin);
        }
        else
            return null;
    }

    @Override
    public AdminDto getAdmin(String nom) {
        Optional<Admin> optionalAdmin=adminRepository.findByNomAndDeleteAtIsNull(nom);
        if(optionalAdmin.isPresent()){
            Admin admin= optionalAdmin.get();
            return adminConverter.toDto(admin);
        }
        else
            return null;
    }

    @Override
    public List<AdminDto> getAll() {
        List<Admin> adminList=adminRepository.findByDeleteAtIsNull();
        return adminList.stream()
                .map(adminConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDto updateAdmin(UUID id, AdminDto adminDto) {
        Optional<Admin> optionalAdmin=adminRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setNom(adminDto.getNom());
            admin.setAdresseMail(adminDto.getAdresseMail());
            return adminConverter.toDto(adminRepository.save(admin));
        }
        else
            return null;
    }

    @Override
    public AdminDto updateEntryKey(UUID id, AdminDto adminDto) {
        List<Admin> adminList=adminRepository.findByDeleteAtIsNull();
        Optional<Admin> optionalAdmin=adminRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalAdmin.isPresent()){
            if(optionalAdmin.get().equals(adminList.getFirst())){
                Admin admin= optionalAdmin.get();
                admin.setEntryKey(adminDto.getEntryKey());
                System.err.println(admin.getEntryKey());
                admin=adminRepository.save(admin);
                for(Admin admin1:adminList){
                    admin1.setEntryKey(admin.getEntryKey());
                    adminRepository.save(admin1);
                }
                return adminConverter.toDto(admin);
            }
            else{
                List<ErrorModel> errorModelList=new ArrayList<>();
                ErrorModel errorModel=new ErrorModel();
                errorModel.setCode("AUTHENTIFICATION_FAILED");
                errorModel.setMessage("Cet admin n'a pas le droit de changer la clé d'entrée C'est le premier dans la liste des admins qui en a le droit veuillez consulter celle-ci et/ou le contacter(L'admin)");
                errorModelList.add(errorModel);
                throw new BusinessException(errorModelList);
            }
        }
        else
            return null;
    }

    @Override
    public boolean deleteAdmin(UUID id) {
        Optional<Admin> optionalAdmin=adminRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setDeleteAt(LocalDateTime.now());
            adminRepository.save(admin);
            return true;
        }
        else
            return false;
    }
}
