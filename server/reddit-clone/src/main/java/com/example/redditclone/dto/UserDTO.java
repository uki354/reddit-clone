package com.example.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private String cakeDay;

}
