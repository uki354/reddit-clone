package com.example.redditclone.service;

import com.example.redditclone.Model.*;
import com.example.redditclone.dto.VoteDTO;
import com.example.redditclone.exception.RedditCloneException;
import com.example.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final PostService postService;
    private final AuthService authService;
    private final NotificationServiceImpl notificationService;
    private final CommentService commentService;


    @Override
    public void vote(VoteDTO voteDTO){
        if (voteDTO.getVoteObject().equals(VoteObject.POST)){
            validatePostVote(voteDTO);
        }else validateCommentVote(voteDTO);

        voteAndSave(voteDTO);
    }

    private void validatePostVote(VoteDTO voteDTO){
        Post post = postService.getPost(voteDTO.getId());
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());
        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDTO.getVoteType())){
            throw new RedditCloneException("You have voted for this post");
        }
    }



    private void validateCommentVote(VoteDTO voteDTO){
        Comment comment = commentService.findComment(voteDTO.getId());
        Optional<Vote> voteByCommentAndUser = voteRepository.findTopByCommentAndUserOrderByIdDesc(comment, authService.getCurrentUser());
        if(voteByCommentAndUser.isPresent() && voteByCommentAndUser.get().getVoteType().equals(voteDTO.getVoteType())){
            throw new RedditCloneException("You have voted for this comment");
        }


    }

    private void voteAndSave(VoteDTO voteDTO){
        Vote vote;
        if(voteDTO.getVoteObject().equals(VoteObject.POST)){
            Post post = postService.getPost(voteDTO.getId());
            if(voteDTO.getVoteType().equals(VoteType.UPVOTE)){
                post.setVoteCount(post.getVoteCount() + 1);
            }else post.setVoteCount(post.getVoteCount() -1);
            postService.savePost(post);
            vote = mapToVote(voteDTO,post,null);
        }else{
            Comment comment = commentService.findComment(voteDTO.getId());
            if(voteDTO.getVoteType().equals(VoteType.UPVOTE)){
                comment.setVoteCount(comment.getVoteCount() + 1);
            }else comment.setVoteCount(comment.getVoteCount() -1);
            commentService.saveComment(comment);
            vote = mapToVote(voteDTO,comment.getPost(),comment);
        }
        voteRepository.save(vote);

    }

    private Vote mapToVote(VoteDTO voteDTO,Post post, Comment comment){
        return Vote.builder()
                .voteType(voteDTO.getVoteType())
                .user(authService.getCurrentUser())
                .post(post)
                .voteObject(voteDTO.getVoteObject())
                .comment(comment)
                .build();
    }
}
