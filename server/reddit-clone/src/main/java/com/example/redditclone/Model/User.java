package com.example.redditclone.Model;

import com.example.redditclone.anotation.ValidImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reddit_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Date createdAt;
    private boolean isEnabled;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;
    @Lob
    private byte[] image;



}
