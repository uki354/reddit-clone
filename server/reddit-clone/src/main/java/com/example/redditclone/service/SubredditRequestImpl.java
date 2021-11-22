package com.example.redditclone.service;

import com.example.redditclone.Model.Post;
import com.example.redditclone.Model.Subreddit;
import com.example.redditclone.Model.User;
import com.example.redditclone.dto.PostDTO;
import com.example.redditclone.dto.SubredditDTO;

import com.example.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubredditRequestImpl implements SubredditService{

    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final UserService userService;



    @Override
    public void makeSubreddit(SubredditDTO subredditDTO) {
        if(!subredditRepository.existsByName(subredditDTO.getName())){
            User user = authService.getCurrentUser();
            subredditRepository.save(Subreddit.builder()
            .name(subredditDTO.getName())
            .description(subredditDTO.getDescription())
            .user(user)
            .createdAt(Date.from(Instant.now()))
            .build());
        }

    }


    @Override
    public SubredditDTO getSubredditDTO(String name) {
        Subreddit subreddit =   subredditRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Subreddit not found " + name));

        return SubredditDTO.builder()
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .owner(subreddit.getUser().getUsername())

                .build();

    }

    @Override
    public List<SubredditDTO> getSubredditsForUser(String username) {
        User user = userService.findUser(username);
        List<Subreddit> subredditList = subredditRepository.findByUser(user);
        return subredditList.stream().map(subreddit -> SubredditDTO.builder()
                .name(subreddit.getName())
                .owner(subreddit.getUser().getUsername())
                .description(subreddit.getDescription()).build()).collect(Collectors.toList());
    }


    @Override
    public Subreddit getSubreddit(String name) {
        return subredditRepository.findByName(name).orElseThrow(()->new NoSuchElementException("Subreddit not found " + name));
    }
}
