package com.example.redditclone.service;


import com.example.redditclone.Model.Subreddit;
import com.example.redditclone.Model.User;
import com.example.redditclone.dto.SubredditDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SubredditService {

    void makeSubreddit(SubredditDTO subredditDTO, MultipartFile profile, MultipartFile cover) throws IOException;
    SubredditDTO getSubredditDTO(String name);
    Subreddit getSubreddit(String name);
    List<SubredditDTO> getSubredditsForUser(String username);
    void updateSubredditProfile(MultipartFile file, String name) throws  IOException;
    void updateSubredditCover(MultipartFile file, String name) throws  IOException;
    void joinSubreddit(String name);
    void unJoinSubreddit(String name);
    SubredditDTO mapToDTO(Subreddit subreddit);

}
