package com.projet.foodGo.exeptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CustomHandlerException {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<List<ErrorModel>> handleBusinessException(BusinessException bex){
        System.err.println("Une Exception coté client a été levée");
        return new ResponseEntity<>(bex.getErrorModels(), HttpStatus.BAD_REQUEST);
    }
}
