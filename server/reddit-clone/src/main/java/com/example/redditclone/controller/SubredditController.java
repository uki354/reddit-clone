package com.example.redditclone.controller;



import com.example.redditclone.dto.SubredditDTO;
import com.example.redditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class SubredditController {

    private final SubredditService subredditService;


    @PostMapping("/newsub")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void makeNewSubreddit(@RequestBody SubredditDTO subredditDTO){
        subredditService.makeSubreddit(subredditDTO);
    }


    @GetMapping("/subreddit/{name}")
    public ResponseEntity<SubredditDTO> findSubreddit(@PathVariable String name){
        return new ResponseEntity<>( subredditService.getSubreddit(name), HttpStatus.OK);

    }

    @GetMapping("/subreddit/user/{name}")
    public ResponseEntity<List<SubredditDTO>> allSubredditsByUser(@PathVariable String name){
        return new ResponseEntity<>(subredditService.getSubredditsForUser(name),HttpStatus.OK);
    }





}
