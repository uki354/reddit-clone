package com.example.redditclone.service;

import com.example.redditclone.Model.Post;
import com.example.redditclone.Model.Subreddit;
import com.example.redditclone.Model.User;
import com.example.redditclone.dto.PostDTO;

import com.example.redditclone.exception.RedditCloneException;
import com.example.redditclone.repository.PostRepository;

import com.example.redditclone.utility.DateUtility;
import com.ocpsoft.pretty.time.PrettyTime;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final AuthService authService;
    private final CommentService commentService;
    private final SubredditService subredditService;
    private final PrettyTime prettyTime;
    private final UserService userService;
    private final DateUtility dateUtility;

    public static final int PAGE_LIMIT = 20;


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
    public List<PostDTO> getPostDTOsForSubreddit(String name, int page, String filter) {
        Pageable pageable = PageRequest.of(page,PAGE_LIMIT);
        Date[] dates = checkFilter(filter);
        Subreddit subreddit = subredditService.getSubreddit(name);
        List<Post> posts = postRepository.findAllBySubredditAndCreatedAtBetweenOrderByVoteCountDesc
                (subreddit, dates[0], dates[1],pageable);
        return posts.stream().map(this::mapToPostDTO).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getLatestPostDTOsForSubreddit(String name, int page) {
        Pageable pageable = PageRequest.of(page,PAGE_LIMIT);
        Subreddit subreddit = subredditService.getSubreddit(name);
        List<Post> posts = postRepository.findAllBySubredditOrderByCreatedAt(subreddit,pageable);
        return posts.stream().map(this::mapToPostDTO).collect(Collectors.toList());
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }


    @Override
    public List<PostDTO> getPostDTOsForUser(String username, int page, String sort) {
        DateUtility dateUtility = new DateUtility();

        Pageable pageable = PageRequest.of(page,20,Sort.by(checkSort(sort)).descending());
        User user = userService.findUser(username);
        List<Post> postList = postRepository.findAllByUserAndCreatedAtBetween(user, dateUtility.firstDayOfWeek(), dateUtility.lastDayOfWeek());
        return postList.stream().map(this::mapToPostDTO).collect(Collectors.toList());
    }

    private PostDTO mapToPostDTO(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .voteCount(post.getVoteCount())
                .user(post.getUser().getUsername())
                .duration(prettyTime.format(post.getCreatedAt()))
                .commentCount(post.getCommentCount())
                .title(post.getTitle())
                .content(post.getContent())
                .subreddit(post.getSubreddit().getName())
                .build();
    }

    @NotNull(message = "Not valid filter")
    private Date[] checkFilter(String filter) {

        switch (filter.toLowerCase()){
            case "alltime": return new Date[]{DateUtility.FAR_PAST, DateUtility.FAR_FUTURE};
            case "year": return new Date[]{dateUtility.firstDayOfYear(),dateUtility.lastDayOfYear()};
            case "month": return new Date[]{dateUtility.firstDayOfMonth(), dateUtility.lastDayOfMonth()};
            case "week": return new Date[]{dateUtility.firstDayOfWeek(),dateUtility.lastDayOfWeek()};
            case "today": return new Date[]{dateUtility.previousDay(), dateUtility.nextDay()};
            default: return null;
        }

    }



    private String checkSort(String sort){
        switch (sort.toLowerCase()){
            case "comments": return "commentCount";
            case "votes"   : return "voteCount";
            case "date"    : return "createdAt";
            default: return "id";

        }
    }


}
