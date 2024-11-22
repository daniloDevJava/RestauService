package com.projet.foodGo.controller;

import com.projet.foodGo.external.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-management")
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<Void> addUser(@RequestBody RegisterRequest userDto){
        return null;
    }
}
