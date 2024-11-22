package com.projet.foodGo.exeptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException{
    List<ErrorModel> errorModels;

    public BusinessException(List<ErrorModel> errorModels){
        this.errorModels=errorModels;
    }
}
