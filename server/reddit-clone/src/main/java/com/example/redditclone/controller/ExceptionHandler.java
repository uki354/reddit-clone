package com.example.redditclone.controller;


import com.example.redditclone.exception.ApiError;
import com.example.redditclone.exception.RedditCloneException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter
            (MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String error = ex.getParameterName() + " is missing";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,ex.getLocalizedMessage(),error);

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }















}
