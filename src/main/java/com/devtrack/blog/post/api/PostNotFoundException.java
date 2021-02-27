package com.devtrack.blog.post.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Getter
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Post Not Found")
public class PostNotFoundException extends RuntimeException  {
    public PostNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}