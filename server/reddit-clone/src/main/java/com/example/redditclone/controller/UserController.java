package com.example.redditclone.controller;


import com.example.redditclone.anotation.ValidImage;
import com.example.redditclone.dto.UserDTO;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;



    @PostMapping(value = "/setimage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void setUserImage(@ValidImage @RequestParam("image") MultipartFile multipartFile){
        userService.setUserImage(multipartFile);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username){
        return new ResponseEntity<>(userService.getUserDTO(username), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<UserDTO> getLoggedUser(){
        return new ResponseEntity<>(userService.getLoggedUser(),HttpStatus.OK);
    }


}
