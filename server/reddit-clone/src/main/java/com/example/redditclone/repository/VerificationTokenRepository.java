package com.example.redditclone.repository;

import com.example.redditclone.Model.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

   Optional<VerificationToken> findByToken(String token);
}
