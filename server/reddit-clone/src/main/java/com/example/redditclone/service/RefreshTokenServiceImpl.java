package com.example.redditclone.service;

import com.example.redditclone.Model.RefreshToken;
import com.example.redditclone.exception.RedditCloneException;
import com.example.redditclone.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements  RefreshTokenService {

    private  final RefreshTokenRepository refreshTokenRepository;


    @Override
    public void saveToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void deleteToken(RefreshToken token) {
        refreshTokenRepository.delete(token);

    }

    @Override
    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);

    }

    @Override
    public RefreshToken findToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> new RedditCloneException("Invalid token"));
    }
}
