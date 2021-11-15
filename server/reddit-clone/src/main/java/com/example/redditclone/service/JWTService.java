package com.example.redditclone.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.redditclone.Model.RefreshToken;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public interface JWTService {

    String generateJwtToken(User user, HttpServletRequest request);
    DecodedJWT verifyJwtToken(String token);
    String generateRefreshToken(User user, HttpServletRequest request);
    void deleteRefreshToken(String token);
    void saveRefreshToken(RefreshToken token);
    DecodedJWT verifyRefreshToken(String token);
    void refreshTokenRotation(HttpServletRequest request, HttpServletResponse response) throws IOException;




}
