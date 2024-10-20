package com.projet.foodGo.mapper;

import com.projet.foodGo.dto.UserDto;
import com.projet.foodGo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User toEntity(UserDto userDto){
         User user=new User();
         user.setNomEtPrenom(userDto.getNomEtPrenom());
         user.setAdresseMail(user.getAdresseMail());
         return user;
    }
    public UserDto toDto(User user){
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setNomEtPrenom(user.getNomEtPrenom());
        userDto.setCreateAt(user.getCreateAt());
        userDto.setUpdateAt(user.getUpdateAt());
        return userDto;
    }
}
