package com.example.redditclone.controller;


import com.example.redditclone.dto.VoteDTO;
import com.example.redditclone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class VoteController {

    private final VoteService voteService;


    @PostMapping("/vote")
    @ResponseStatus(HttpStatus.OK)
    public void voteForPost(@RequestBody VoteDTO voteDTO){ voteService.vote(voteDTO);}
}
