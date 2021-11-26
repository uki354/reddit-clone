package com.example.redditclone.repository;

import com.example.redditclone.Model.Post;
import com.example.redditclone.Model.Subreddit;
import com.example.redditclone.Model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findBySubreddit(Subreddit subreddit);
    List<Post> findAllBySubreddit(Subreddit subreddit, Pageable pageable);
    List<Post> findAllByUser(User user, Pageable pageable);
    List<Post> findAllByUserAndCreatedAtBetween(User user, Date start, Date end);
    List<Post> findAllBySubredditAndCreatedAtBetweenOrderByVoteCountDesc(Subreddit subreddit, Date start, Date end, Pageable pageable);
    List<Post> findAllBySubredditOrderByCreatedAt(Subreddit subreddit, Pageable pageable);






}
