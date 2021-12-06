package com.example.redditclone.dto;

import com.example.redditclone.Model.VoteObject;
import com.example.redditclone.Model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class VoteDTO {

    private VoteType voteType;
    private VoteObject voteObject;
    private int id;
}
