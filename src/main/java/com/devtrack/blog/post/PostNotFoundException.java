package com.devtrack.blog.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Post Not Found")
public class PostNotFoundException extends RuntimeException  {
    public PostNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}