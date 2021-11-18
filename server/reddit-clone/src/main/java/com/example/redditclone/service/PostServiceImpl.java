package com.example.redditclone.service;

import com.example.redditclone.Model.Post;
import com.example.redditclone.Model.Subreddit;
import com.example.redditclone.Model.User;
import com.example.redditclone.dto.PostDTO;

import com.example.redditclone.repository.PostRepository;

import com.ocpsoft.pretty.time.PrettyTime;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommentService commentService;
    private final SubredditService subredditService;
    private final PrettyTime prettyTime;


    @Override
    public void createPost(PostDTO postDTO) {
        User user = authService.getCurrentUser();
        Subreddit subreddit = subredditService.getSubreddit(postDTO.getSubreddit());

        postRepository.save(Post.builder()
                .createdAt(Date.from(Instant.now()))
                .content(postDTO.getContent())
                .subreddit(subreddit)
                .user(user)
                .title(postDTO.getTitle())
                .voteCount(0)
                .build());

    }

    @Override
    public void editPostContent(PostDTO postDTO) {
        Post post = postRepository.getById(postDTO.getId());
        post.setContent(postDTO.getContent());
        postRepository.save(post);

    }

    @Override
    public void deletePost(PostDTO postDTO) {
        Post post = getPost(postDTO);
        postRepository.delete(post);
    }

    @Override
    public Post getPost(int id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id " + id));
    }

    @Override
    public Post getPost(PostDTO postDTO) {
        return postRepository.findById(postDTO.getId())
                .orElseThrow(()-> new NoSuchElementException("Post not found"));
    }

    @Override
    public PostDTO getPostDTO(int id) {
        Post post = getPost(id);
        return PostDTO.builder()
                .id(post.getId())
                .user(post.getUser().getUsername())
                .commentCount(commentService.getCommentCountForPost(post))
                .subreddit(post.getSubreddit().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    @Override
    public void deletePost(int id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }


    @Override
    public List<PostDTO> getPostDTOsForSubreddit(String name, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset,limit);
        Subreddit subreddit = subredditService.getSubreddit(name);
        List<Post> postList = postRepository.findAllBySubreddit(subreddit,pageable);
        return postList.stream().map(this::mapToPostDTO).collect(Collectors.toList());
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }


    private PostDTO mapToPostDTO(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .voteCount(post.getVoteCount())
                .user(post.getUser().getUsername())
                .duration(prettyTime.format(post.getCreatedAt()))
                .commentCount(commentService.getCommentCountForPost(post))
                .title(post.getTitle())
                .content(post.getContent())
                .subreddit(post.getSubreddit().getName())
                .build();
    }
}
