package com.example.redditclone.service;


import com.example.redditclone.Model.Comment;
import com.example.redditclone.Model.Post;
import com.example.redditclone.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    Integer getCommentCountForPost(Post post);
    List<CommentDTO> getCommentDTOsForPost(int postId);
    void createComment(CommentDTO commentDTO);
    Comment findComment(int id);
    void saveComment(Comment comment);



}
