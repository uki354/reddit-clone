package com.example.redditclone.repository;

import com.example.redditclone.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
}
