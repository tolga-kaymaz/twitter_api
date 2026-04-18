package com.workintech.twitter_api.exceptions;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends TwitterApiException {
    public CommentNotFoundException(Long id) {
        super("Yorum bulunamadı. Id: " + id, HttpStatus.NOT_FOUND);
    }

    public CommentNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
