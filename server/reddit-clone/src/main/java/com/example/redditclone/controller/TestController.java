package com.example.redditclone.controller;

import com.example.redditclone.Model.User;
import com.example.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {

    private final  UserRepository userRepository;


    @GetMapping("/users")
    public ResponseEntity<List<User>> users(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }
}
