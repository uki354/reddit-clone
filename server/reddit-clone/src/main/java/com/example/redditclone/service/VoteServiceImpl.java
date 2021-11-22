package com.example.redditclone.service;

import com.example.redditclone.Model.Post;
import com.example.redditclone.Model.Vote;
import com.example.redditclone.Model.VoteType;
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


    @Override
    public void vote(VoteDTO voteDTO) {
        Post post = postService.getPost(voteDTO.getPostId());
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser());
        if(voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDTO.getVoteType())){
            throw new RedditCloneException("You have voted for this post");
        }
        if (VoteType.UPVOTE.equals(voteDTO.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }else post.setVoteCount(post.getVoteCount() -1);

        voteRepository.save(mapToVote(voteDTO,post));
        postService.savePost(post);

    }

    private Vote mapToVote(VoteDTO voteDTO,Post post){
        return Vote.builder()
                .voteType(voteDTO.getVoteType())
                .user(authService.getCurrentUser())
                .post(post)
                .build();
    }
}
