package com.example.redditclone.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private VoteType voteType;
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    @ManyToOne
    @Nullable
    private Comment comment;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private VoteObject voteObject;
}
