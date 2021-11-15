package com.example.redditclone.service;

import com.example.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl {


    private final UserRepository userRepository;

}
