package com.example.redditclone.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.redditclone.Model.RefreshToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JWTServiceImpl implements JWTService{

        private final RefreshTokenService refreshTokenService;
        private final UserDetailsService userDetailsService;

    @Override
    public String generateJwtToken(User user, HttpServletRequest request) {
        Algorithm algorithm = getAlgorithm();
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    @Override
    public DecodedJWT verifyJwtToken(String token) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
       return  verifier.verify(token);



    }

    @Override
    public String generateRefreshToken(User user, HttpServletRequest request) {
        Algorithm algorithm = getAlgorithm();
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuer(request.getRequestURL().toString())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 2592000000L ))
                .withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

    }

    @Override
    public DecodedJWT verifyRefreshToken(String token){
        RefreshToken refreshToken = refreshTokenService.findToken(token);
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(refreshToken.getToken());
    }



    private Algorithm getAlgorithm(){
        return Algorithm.HMAC256("reddit".getBytes());
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenService.findToken(token);
        refreshTokenService.deleteToken(refreshToken);

    }

    @Override
    public void saveRefreshToken(RefreshToken token) {
        refreshTokenService.saveToken(token);
    }

    @Override
    public void refreshTokenRotation( HttpServletRequest request, HttpServletResponse response) throws IOException {
        //          REFRESH TOKEN ROTATION FLOW :
        // - Get tokens tokens from json request body
        // - validate access token(ignore time exp)
        // - validate refresh token (verify it & search in DB)
        // - invalidate refresh token(delete from db)
        // - give new pair of tokens
        // - save new refresh token to db

        Map<String,String> tokens = getTokensFromRequest(request);
        String access_token = tokens.get("access_token");
        String refresh_token = tokens.get("refresh_token");

        try {
              verifyJwtToken(access_token);
        }catch (TokenExpiredException ignored){
            try {

                DecodedJWT decoded_refresh_token =  verifyRefreshToken(refresh_token);
                deleteRefreshToken(refresh_token);

                User user = (User) userDetailsService.loadUserByUsername(decoded_refresh_token.getSubject());

                String new_access_token   =  generateJwtToken(user,request);
                String new_refresh_token  =  generateRefreshToken(user,request);

                RefreshToken refreshToken =  new RefreshToken();
                refreshToken.setToken(new_refresh_token);
                saveRefreshToken(refreshToken);


                Map<String,String> new_tokens = new HashMap<>();

                new_tokens.put("access_token", new_access_token);
                new_tokens.put("refresh_token", new_refresh_token);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),new_tokens);

            }catch (Exception e ){
                response.sendError(HttpStatus.FORBIDDEN.value());
            }
        }
        catch (Exception exception){
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
        }



    private Map<String,String> getTokensFromRequest(HttpServletRequest request)throws IOException{
        return new ObjectMapper().readValue(request.getInputStream(),Map.class);
    }
}
