package com.example.redditclone.service;

import com.example.redditclone.Model.Comment;
import com.example.redditclone.Model.Post;
import com.example.redditclone.dto.CommentDTO;
import com.example.redditclone.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final AuthService authService;
    @Autowired @Lazy
    private PostService postService;

    @Override
    public Integer getCommentCountForPost(Post post) {
        return commentRepository.countCommentByPost(post);
    }

    @Override
    public List<CommentDTO> getCommentDTOsForPost(int postId) {
        Post post = postService.getPost(postId);
        List<Comment> commentList = commentRepository.getAllByPost(post);
        return commentList.stream().map(this::mapToCommentDTO).collect(Collectors.toList());
    }

    @Override
    public void createComment(CommentDTO commentDTO) {
        commentRepository.save(mapToComment(commentDTO));
    }


    private Comment mapToComment(CommentDTO commentDTO){
        Post post = postService.getPost(commentDTO.getPostId());
        return Comment.builder()
                .content(commentDTO.getContent())
                .createdAt(Date.from(Instant.now()))
                .user(authService.getCurrentUser())
                .post(post)
                .build();
    }

    private CommentDTO mapToCommentDTO(Comment comment){
        return CommentDTO.builder()
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .user(comment.getUser().getUsername())
                .build();
    }
}
