package com.example.redditclone.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {


    @GetMapping("/api/auth/documentation")
    public String showDocs(){
        return "index";
    }
}
