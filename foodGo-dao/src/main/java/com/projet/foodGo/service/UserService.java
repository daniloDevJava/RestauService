package com.projet.foodGo.service;

import com.projet.foodGo.dto.UserDto;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

/**
 * The interface User service.
 */
@Service
public interface UserService {
    /**
     * Create user user dto.
     *
     * @param userDto the user dto
     * @return the user dto
     */
    UserDto createUser(UserDto userDto);

    /**
     * Gets all users.
     *
     * @return the all users
     */
    List<UserDto> getAllUsers();

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    UserDto getUser(UUID id);

    /**
     * Update user user dto.
     *
     * @param id      the id
     * @param userDto the user dto
     * @return the user dto
     */
    UserDto updateUser(UUID id,UserDto userDto);

    /**
     * Update user pass word user dto.
     *
     * @param id          the id
     * @param userDto     the user dto
     * @param oldPassWord the old pass word
     */
    void updateUserPassWord(UUID id, UserDto userDto, String oldPassWord);

    /**
     * Delete user boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean deleteUser(UUID id);

    /**
     * Update montant compte user dto.
     *
     * @param id      the id
     * @param userDto the user dto
     * @return the user dto
     */
    UserDto updateMontantCompte(UUID id, UserDto userDto);
}
