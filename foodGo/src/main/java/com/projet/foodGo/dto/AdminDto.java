package com.projet.foodGo.dto;



import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class AdminDto extends UserDto {

    private UUID EntryKey;


}
