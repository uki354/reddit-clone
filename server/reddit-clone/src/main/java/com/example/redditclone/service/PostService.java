package com.example.redditclone.service;

import com.example.redditclone.Model.Post;
import com.example.redditclone.dto.PostDTO;

import java.util.List;

public interface PostService {

    void createPost(PostDTO postDTO);
    void editPostContent(PostDTO postDTO);
    void deletePost(PostDTO postDTO);
    void deletePost(int id);
    Post getPost(int id);
    Post getPost(PostDTO postDTO);
    PostDTO getPostDTO(int id);
    List<PostDTO> getPostDTOsForSubreddit(String name, int page, String filter);
    void savePost(Post post);
    List<PostDTO> getPostDTOsForUser(String username, int page, String sort);
    List<PostDTO> getLatestPostDTOsForSubreddit(String  name, int page);

}
