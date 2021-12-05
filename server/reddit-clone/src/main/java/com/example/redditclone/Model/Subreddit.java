package com.example.redditclone.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String name;
    private Date createdAt;
    @Lob
    private String  description;
    @Lob
    private byte[] profileImage;
    @Lob
    private byte[] coverImage;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "subreddit",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Post> posts;
    @ManyToMany
    private List<User> joinedUsers;




}
