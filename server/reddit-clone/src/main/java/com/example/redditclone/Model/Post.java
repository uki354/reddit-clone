package com.example.redditclone.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date createdAt;
    @ManyToOne
    private User user;
    private String title;
    private String content;
    @ManyToOne
    private Subreddit subreddit;
    @OneToMany
    private List<Comment> comments;






}
