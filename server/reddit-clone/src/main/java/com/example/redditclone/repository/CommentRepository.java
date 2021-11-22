package com.example.redditclone.repository;

import com.example.redditclone.Model.Comment;
import com.example.redditclone.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository <Comment,Integer> {

    Integer countCommentByPost(Post post);
    List<Comment> getAllByPost(Post post);

}
