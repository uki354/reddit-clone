package com.example.redditclone.controller;


import com.example.redditclone.dto.PostDTO;
import com.example.redditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PostController {

    private  final PostService postService;

    @PostMapping("/newpost")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewPost(@RequestBody PostDTO postDTO){
        postService.createPost(postDTO);
    }

    @DeleteMapping("/deletepost")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@RequestBody PostDTO postDTO){
        postService.deletePost(postDTO);
    }

    @DeleteMapping("/deletepost/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePostById(@PathVariable int id){
        postService.deletePost(id);
    }

    @PostMapping("/editpost")
    @ResponseStatus(HttpStatus.OK)
    public void editPost(@RequestBody PostDTO postDTO){
        postService.editPostContent(postDTO);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable int id){
        return  new ResponseEntity<>(postService.getPostDTO(id),HttpStatus.OK);
    }
    @GetMapping("/posts/{subreddit}/{offset}")
    public ResponseEntity<List<PostDTO>> getPostsWithOffset(@PathVariable int offset,
                                                            @PathVariable String subreddit){
        return new ResponseEntity<>(postService.getPostDTOsForSubreddit(subreddit,offset,20),HttpStatus.OK);
    }






}
