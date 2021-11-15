package com.example.redditclone.service;


import com.example.redditclone.Model.User;

import java.util.List;

public interface UserService {

    User getUserById(int id);
    List<User> getAllUsers();




}
