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
    @GetMapping("/posts/r/{subreddit}")
    public ResponseEntity<List<PostDTO>> getPostsForSubredditSortedByVote
            (@PathVariable String subreddit,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "today") String filter){
        return new ResponseEntity<>(postService.getPostDTOsForSubreddit(subreddit,page,filter),HttpStatus.OK);
    }

    @GetMapping("/posts/r/{subreddit}/new")
    public ResponseEntity<List<PostDTO>> getLatestPostsForSubreddit(@PathVariable String subreddit,
                                                                    @RequestParam(required = false, defaultValue = "0") int page){
        return new ResponseEntity<>(postService.getLatestPostDTOsForSubreddit(subreddit, page),HttpStatus.OK);
    }

    @GetMapping("/posts/{username}")
    public ResponseEntity<List<PostDTO>> getPostsForUser
            (@PathVariable String username,
             @RequestParam(required = false, defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "createdAt") String sort){
        return new ResponseEntity<>(postService.getPostDTOsForUser(username,page,sort), HttpStatus.OK);
    }








}
