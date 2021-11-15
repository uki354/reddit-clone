package com.example.redditclone.security;

import com.example.redditclone.Model.RefreshToken;
import com.example.redditclone.service.JWTService;
import com.example.redditclone.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

    private  final AuthenticationManager authenticationManager;
    private final JWTService jwtService;



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try{
            Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
             String username = requestMap.get("username");
              String password = requestMap.get("password");

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password);
            return authenticationManager.authenticate(token);


        }catch (IOException e){
            e.printStackTrace();
        }


        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();


        String access_token = jwtService.generateJwtToken(user,request);
        String refresh_token = jwtService.generateRefreshToken(user,request);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(refresh_token);
        jwtService.saveRefreshToken(refreshToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);



    }
}
