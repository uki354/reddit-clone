package com.example.redditclone.service;

import com.example.redditclone.Model.User;
import com.example.redditclone.dto.UserDTO;
import com.example.redditclone.exception.RedditCloneException;
import com.example.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.Base64;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;
    private final AuthService authService;


    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found "  + username));
    }

    @Override
    public void setUserImage(MultipartFile multipartFile) {
        try {
            User user = authService.getCurrentUser();
            System.out.println(multipartFile.getContentType());
            user.setImage(multipartFile.getBytes());
            userRepository.save(user);
        }catch (Exception exception){
            throw  new RedditCloneException("Error occurred while setting user image");
        }
    }

    @Override
    public UserDTO getUserDTO(String username){
        User user = findUser(username);
        return mapToDTO(user);

    }

    private UserDTO mapToDTO(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .image(Base64.getEncoder().encodeToString(user.getImage()))
                .cakeDay(user.getCreatedAt().toString())
                .build();
    }



}
