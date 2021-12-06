package com.example.redditclone.service;


import com.example.redditclone.Model.Subreddit;
import com.example.redditclone.Model.User;

import com.example.redditclone.dto.SubredditDTO;

import com.example.redditclone.exception.RedditCloneException;
import com.example.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
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
    public void makeSubreddit(SubredditDTO subredditDTO, MultipartFile profile, MultipartFile cover) throws IOException {
        if(!subredditRepository.existsByName(subredditDTO.getName())){
            User user = authService.getCurrentUser();
            subredditRepository.save(Subreddit.builder()
            .name(subredditDTO.getName())
            .description(subredditDTO.getDescription())
            .user(user)
            .profileImage(profile.getBytes())
            .coverImage(cover.getBytes())
            .createdAt(Date.from(Instant.now()))
            .build());
        }else throw new RedditCloneException("Name already in use");

    }

    @Override
    public void updateSubredditProfile(MultipartFile file, String name) throws IOException{
        Subreddit subreddit = getSubreddit(name);
        subreddit.setProfileImage(file.getBytes());
        subredditRepository.save(subreddit);
    }

    @Override
    public void updateSubredditCover(MultipartFile file, String name) throws IOException{
        Subreddit subreddit = getSubreddit(name);
        subreddit.setCoverImage(file.getBytes());
        subredditRepository.save(subreddit);

    }

    @Override
    public SubredditDTO getSubredditDTO(String name) {
        Subreddit subreddit =   subredditRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Subreddit not found " + name));

        return mapToDTO(subreddit);
    }

    @Override
    public List<SubredditDTO> getSubredditsForUser(String username) {
        User user = userService.findUser(username);
        List<Subreddit> subredditList = subredditRepository.findByUser(user);
        return subredditList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    @Override
    public Subreddit getSubreddit(String name) {
        return subredditRepository.findByName(name).orElseThrow(()->new NoSuchElementException("Subreddit not found " + name));
    }

    @Override
    public void joinSubreddit(String name) {
        User user = authService.getCurrentUser();
        Subreddit subreddit = getSubreddit(name);

        subreddit.getJoinedUsers().add(user);
        subredditRepository.save(subreddit);
    }

    @Override
    public void unJoinSubreddit(String name) {
        User user = authService.getCurrentUser();
        Subreddit subreddit = getSubreddit(name);

        subreddit.getJoinedUsers().remove(user);
        subredditRepository.save(subreddit);
    }

    public SubredditDTO mapToDTO(Subreddit subreddit){
        return SubredditDTO.builder()
                .name(subreddit.getName())
                .description(subreddit.getDescription())
                .owner(subreddit.getUser().getUsername())
                .memberCount(subreddit.getJoinedUsers().size())
                .build();

    }
}
