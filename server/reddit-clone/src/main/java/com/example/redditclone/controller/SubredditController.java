package com.example.redditclone.controller;



import com.example.redditclone.anotation.ValidImage;
import com.example.redditclone.dto.SubredditDTO;
import com.example.redditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Validated
public class SubredditController {

    private final SubredditService subredditService;


    @PostMapping(value = "/newsub", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public void makeNewSubreddit(@RequestPart("data") SubredditDTO subredditDTO,
                                 @ValidImage @RequestPart("profile")MultipartFile multipartProfile,
                                 @ValidImage @RequestPart("cover") MultipartFile multipartCover) throws IOException {
        subredditService.makeSubreddit(subredditDTO, multipartProfile, multipartCover);
    }

    @PutMapping(value = "/edit/profile/{name}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateProfileImg(@PathVariable String name,
                                 @ValidImage @RequestParam("profile") MultipartFile file)throws  IOException{

        subredditService.updateSubredditProfile(file,name);
    }


    @PutMapping(value = "/edit/cover/{name}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateCoverImg(@PathVariable String name,
                               @ValidImage @RequestParam("cover") MultipartFile file)throws  IOException{
        subredditService.updateSubredditCover(file,name);
    }



    @GetMapping("/subreddit/{name}")
    public ResponseEntity<SubredditDTO> findSubreddit(@PathVariable String name){
        return new ResponseEntity<>( subredditService.getSubredditDTO(name), HttpStatus.OK);

    }

    @GetMapping("/subreddit/user/{name}")
    public ResponseEntity<List<SubredditDTO>> allSubredditsByUser(@PathVariable String name){
        return new ResponseEntity<>(subredditService.getSubredditsForUser(name),HttpStatus.OK);
    }

    @GetMapping("/{name}/join")
    @ResponseStatus(code = HttpStatus.OK)
    public void joinSubreddit(@PathVariable String name){
        subredditService.joinSubreddit(name);
    }

    @GetMapping("/{name}/unjoin")
    @ResponseStatus(code = HttpStatus.OK)
    public void unJoinSubreddit(@PathVariable String name){
        subredditService.unJoinSubreddit(name);
    }





}
