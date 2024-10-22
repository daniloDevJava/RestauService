package com.projet.foodGo.service;

import com.projet.foodGo.dto.UserDto;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getAllUsers();
    UserDto getUser(UUID id);
    UserDto updateUser(UUID id,UserDto userDto);
    boolean deleteUser(UUID id);
}
