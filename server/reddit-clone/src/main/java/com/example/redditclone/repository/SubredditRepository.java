package com.example.redditclone.repository;

import com.example.redditclone.Model.Subreddit;
import com.example.redditclone.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Integer> {

    boolean existsByName(String name);
    Optional<Subreddit> findByName(String name);
    List<Subreddit> findByUser(User user);
}
