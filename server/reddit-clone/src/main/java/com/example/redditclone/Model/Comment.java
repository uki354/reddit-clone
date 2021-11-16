package com.example.redditclone.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date createdAt;
    @ManyToOne
    private User user;
    @ManyToOne
    private Post post;
    @Lob
    private String content;

}
