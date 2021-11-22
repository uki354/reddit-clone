package com.example.redditclone.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;


import java.util.Arrays;
import java.util.List;


@Data
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;
    
    public ApiError(HttpStatus status, String message, String errors){
        this.status = status;
        this.message = message;
        this.errors = Arrays.asList(errors);

    }

}
