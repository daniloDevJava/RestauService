package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.UserDto;
import com.projet.foodGo.model.Users;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public Users toEntity(UserDto userDto){
         Users user=new Users();
         user.setNom(userDto.getNom());
         user.setAdresseMail(userDto.getAdresseMail());
         user.setMotDePasse(userDto.getMotDePasse());
         return user;
    }
    public UserDto toDto(Users user){
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setNom(user.getNom());
        userDto.setAdresseMail(user.getAdresseMail());
        userDto.setCreateAt(user.getCreateAt());
        userDto.setUpdateAt(user.getUpdateAt());
        return userDto;
    }
}
