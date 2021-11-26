package com.example.redditclone.controller;

import com.example.redditclone.Model.User;
import com.example.redditclone.repository.UserRepository;
import com.example.redditclone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {

    private final  UserRepository userRepository;
    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/users")
    public ResponseEntity<List<User>> users(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }


    @PostMapping(value = "/setImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void setImage(@RequestParam("image") MultipartFile multipartFile){
        userService.setUserImage(multipartFile);
    }
}
