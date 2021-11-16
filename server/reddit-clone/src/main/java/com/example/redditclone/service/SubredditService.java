package com.example.redditclone.service;


import com.example.redditclone.Model.User;
import com.example.redditclone.dto.SubredditDTO;

import java.util.List;

public interface SubredditService {

    void makeSubreddit(SubredditDTO subredditDTO);
    SubredditDTO getSubreddit(String name);
    List<SubredditDTO> getSubredditsForUser(String username);

}
