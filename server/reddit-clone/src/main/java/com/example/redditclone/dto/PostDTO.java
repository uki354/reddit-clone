package com.example.redditclone.dto;


import com.example.redditclone.Model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private int id;
    private String title;
    private String content;
    private String user;
    private String subreddit;
    private Integer commentCount;
    private String duration;
    private Integer voteCount;


}
