package com.projet.foodGo.service.impl;

import com.projet.foodGo.dto.UserDto;
import com.projet.foodGo.exeptions.BusinessException;
import com.projet.foodGo.exeptions.ErrorModel;
import com.projet.foodGo.mapper.UserConverter;
import com.projet.foodGo.model.Users;
import com.projet.foodGo.repository.UserRepository;
import com.projet.foodGo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

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
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalUsers.isPresent()){
            Users users=optionalUsers.get();
            return userConverter.toDto(users);
        }
        else
            return null;
    }


    @Override
    public UserDto updateUser(UUID id, UserDto userDto) {
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalUsers.isPresent())
        {
            Users users=optionalUsers.get();
            users.setAdresseMail(userDto.getAdresseMail());
            users.setNom(userDto.getNom());
            return userConverter.toDto(userRepository.save(users));
        }
        else
            return null;
    }

    @Override
    public void updateUserPassWord(UUID id, UserDto userDto, String oldPassWord) {
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            if(users.getMotDePasse().equals(oldPassWord))
            {
                users.setMotDePasse(userDto.getMotDePasse());
                userConverter.toDto(userRepository.save(users));
            }
            else
                throw new IllegalArgumentException("Entrez d'abord le bon ancien mot de passe");
        }
    }

    @Override
    public boolean deleteUser(UUID id) {
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalUsers.isPresent()){
            Users users=optionalUsers.get();
            users.setDeleteAt(LocalDateTime.now());
            userRepository.save(users);
            return true;
        }
        else
            return false;
    }

    /**
     * @param id 
     * @param userDto
     * @return
     */
    @Override
    public UserDto updateMontantompte(UUID id, UserDto userDto) {
        Optional<Users> optionalUsers=userRepository.findByIdAndDeleteAtIsNull(id);
        if(optionalUsers.isPresent())
        {
            Users users=optionalUsers.get();
            if(userDto.getMontantCompte()>=0) {
                users.setMontantCompte(userDto.getMontantCompte());
                users=userRepository.save(users);
                System.out.println(users.getMontantCompte());
                return userConverter.toDto(users);
            }
            else{
                List<ErrorModel> errorModelList=new ArrayList<>();
                ErrorModel errorModel=new ErrorModel();
                errorModel.setCode("INVALID_ENTRY");
                errorModel.setMessage("Le montant changé ne peut etre négatif");
                errorModelList.add(errorModel);
                throw new BusinessException(errorModelList);
            }
        }
        else
            throw new IllegalArgumentException("utilisateur non trouvé");
    }

}
