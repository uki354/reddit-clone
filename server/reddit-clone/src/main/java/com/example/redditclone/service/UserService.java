package com.example.redditclone.service;


import com.example.redditclone.Model.User;
import com.example.redditclone.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User findUser(String username);
    void setUserImage(MultipartFile multipartFile);
    UserDTO getUserDTO(String username);
    UserDTO getLoggedUser();




}
