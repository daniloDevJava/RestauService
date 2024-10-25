package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.AdminDto;
import com.projet.foodGo.mapper.AdminConverter;
import com.projet.foodGo.model.Admin;
import com.projet.foodGo.repository.AdminRepository;
import com.projet.foodGo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminConverter adminConverter;
    /**
     * @param adminDto 
     * @return
     */
    @Override
    public AdminDto createAdmin(AdminDto adminDto) {
        Random random=new Random();
        List<Admin> adminList=adminRepository.findByDeleteAtIsNull();
        if(adminList.isEmpty()) {
            adminDto.setEntryKey(String.valueOf(random.nextGaussian()));
            Admin admin=adminRepository.save(adminConverter.toEntity(adminDto));
            return adminConverter.toDto(admin);
        }
        else if(adminList.getFirst().getEntryKey().equals(adminDto.getEntryKey())){
            Admin admin=adminRepository.save(adminConverter.toEntity(adminDto));
            return adminConverter.toDto(admin);
        }
        else {
            throw new IllegalArgumentException("Vous n'etes pas reconnu comme un admin");
        }
    }

    /**
     * @param id 
     * @return
     */
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

    /**
     * @param nom 
     * @return
     */
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

    /**
     * @return 
     */
    @Override
    public List<AdminDto> getAll() {
        List<Admin> adminList=adminRepository.findByDeleteAtIsNull();
        return adminList.stream()
                .map(adminConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @param id 
     * @param adminDto
     * @return
     */
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

    /**
     * @param id
     * @param adminDto
     * @return
     */
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
            else
                return adminConverter.toDto(optionalAdmin.get());
        }
        else
            return null;
    }

    /**
     * @param id 
     * @return
     */
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
