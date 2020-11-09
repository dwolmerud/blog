package com.devtrack.blog.post;

public class PostServiceException extends RuntimeException {

    public PostServiceException() {
    }
    
    public PostServiceException(String message) {
        super(message);
    }

    public PostServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostServiceException(Throwable cause) {
        super(cause);
    }

    public PostServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
