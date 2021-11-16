package com.example.redditclone.service;

import com.example.redditclone.Model.User;
import com.example.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{


    private final UserRepository userRepository;


    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found "  + username));
    }
}
