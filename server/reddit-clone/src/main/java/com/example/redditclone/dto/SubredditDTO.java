package com.example.redditclone.dto;


import com.example.redditclone.Model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDTO {

    private String name;
    private String description;
    private String owner;
    private int memberCount;

}
