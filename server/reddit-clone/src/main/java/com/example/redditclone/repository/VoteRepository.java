package com.example.redditclone.repository;

import com.example.redditclone.Model.Comment;
import com.example.redditclone.Model.Post;
import com.example.redditclone.Model.User;
import com.example.redditclone.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User user);
    Optional<Vote> findTopByCommentAndUserOrderByIdDesc(Comment comment, User user);
}
