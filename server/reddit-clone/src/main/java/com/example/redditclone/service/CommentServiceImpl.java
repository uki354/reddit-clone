package com.example.redditclone.service;

import com.example.redditclone.Model.Post;
import com.example.redditclone.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private  final CommentRepository commentRepository;

    @Override
    public Integer getCommentCountForPost(Post post) {
        return commentRepository.countCommentByPost(post);
    }
}
