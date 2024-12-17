package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.AdminDto;
import com.projet.foodGo.model.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminConverter {
    public Admin toEntity(AdminDto adminDto){
        if(adminDto==null)
            return null;
        Admin admin=new Admin();
        admin.setNom(adminDto.getNom());
        admin.setAdresseMail(adminDto.getAdresseMail());
        admin.setEntryKey(adminDto.getEntryKey());
        admin.setMotDePasse(adminDto.getMotDePasse());
        return admin;

    }
    public AdminDto toDto(Admin admin){
        if(admin==null)
            return null;
        AdminDto adminDto=new AdminDto();
        adminDto.setId(admin.getId());
        adminDto.setNom(admin.getNom());
        adminDto.setAdresseMail(admin.getAdresseMail());
        adminDto.setEntryKey(admin.getEntryKey());
        adminDto.setCreateAt(admin.getCreateAt());
        adminDto.setUpdateAt(admin.getUpdateAt());
        return adminDto;
    }
}
