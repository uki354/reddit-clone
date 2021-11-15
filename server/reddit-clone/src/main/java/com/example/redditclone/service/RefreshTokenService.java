package com.example.redditclone.service;

import com.example.redditclone.Model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    void saveToken(RefreshToken refreshToken);
    void deleteToken(RefreshToken token);
    void deleteToken(String token);
    RefreshToken findToken(String token);



}
