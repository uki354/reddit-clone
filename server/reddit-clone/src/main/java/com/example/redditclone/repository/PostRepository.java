package com.example.redditclone.repository;

import com.example.redditclone.Model.Post;
import com.example.redditclone.Model.Subreddit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findBySubreddit(Subreddit subreddit);
    List<Post> findAllBySubreddit(Subreddit subreddit, Pageable pageable);




}
