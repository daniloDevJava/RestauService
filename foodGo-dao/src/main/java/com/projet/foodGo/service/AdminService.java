package com.projet.foodGo.service;

import com.projet.foodGo.dto.AdminDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Admin service.
 */
@Service
public interface AdminService {
    /**
     * Create admin admin dto.
     *
     * @param adminDto the admin dto
     * @return the admin dto
     */
    AdminDto createAdmin(AdminDto adminDto);

    /**
     * Gets admin.
     *
     * @param id the id
     * @return the admin
     */
    AdminDto getAdmin(UUID id);

    /**
     * Gets admin.
     *
     * @param nom the nom
     * @return the admin
     */
    AdminDto getAdmin(String nom);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<AdminDto> getAll();

    /**
     * Update admin admin dto.
     *
     * @param id       the id
     * @param adminDto the admin dto
     * @return the admin dto
     */
    AdminDto updateAdmin(UUID id,AdminDto adminDto);

    /**
     * Update entry key admin dto.
     *
     * @param id       the id
     * @param adminDto the admin dto
     * @return the admin dto
     */
    AdminDto updateEntryKey(UUID id,AdminDto adminDto);

    /**
     * Delete admin boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteAdmin(UUID id);
}
