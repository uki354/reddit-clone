package com.example.redditclone.controller;

import com.example.redditclone.dto.CommentDTO;
import com.example.redditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/newcomment")
    @ResponseStatus(HttpStatus.CREATED)
    public void makeNewComment(@RequestBody CommentDTO commentDTO){
        commentService.createComment(commentDTO);
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable int postId){
        return new ResponseEntity<>(commentService.getCommentDTOsForPost(postId),HttpStatus.OK);
    }




}
