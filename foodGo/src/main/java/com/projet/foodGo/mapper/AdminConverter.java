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
        admin.setNomEtPrenom(adminDto.getNomEtPrenom());
        admin.setAdresseMail(adminDto.getAdresseMail());
        admin.setEntryKey(adminDto.getEntryKey());
        return admin;

    }
    public AdminDto toDto(Admin admin){
        if(admin==null)
            return null;
        AdminDto adminDto=new AdminDto();
        adminDto.setId(admin.getId());
        adminDto.setNomEtPrenom(admin.getNomEtPrenom());
        adminDto.setAdresseMail(admin.getAdresseMail());
        adminDto.setEntryKey(admin.getEntryKey());
        return adminDto;
    }
}
