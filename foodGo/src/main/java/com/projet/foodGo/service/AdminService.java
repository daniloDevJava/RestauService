package com.projet.foodGo.service;

import com.projet.foodGo.dto.AdminDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AdminService {
    AdminDto createAdmin(AdminDto adminDto);
    AdminDto getAdmin(UUID id);
    AdminDto getAdmin(String nom);
    List<AdminDto> getAll();
    AdminDto updateAdmin(UUID id,AdminDto adminDto);
    boolean deleteAdmin(UUID id);
}
