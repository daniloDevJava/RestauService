package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.UserDto;
import com.projet.foodGo.mapper.UserConverter;
import com.projet.foodGo.model.Users;
import com.projet.foodGo.repository.UserRepository;
import com.projet.foodGo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Users user=userRepository.save(userConverter.toEntity(userDto)) ;
        return userConverter.toDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<Users> ListUsers=userRepository.findByDeleteAtIsNull();
        return ListUsers.stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(UUID id) {
        return null;
    }

    @Override
    public UserDto getUser(String momEtPrenom) {
        return null;
    }

    @Override
    public UserDto updateUser(UUID id, UserDto userDto) {
        return null;
    }

    @Override
    public boolean deleteUser(UUID id) {
        return false;
    }
}
