package com.workintech.twitter_api.exceptions;

import org.springframework.http.HttpStatus;

public class RetweetNotFoundException extends TwitterApiException{

    public RetweetNotFoundException(Long id) {
        super("Retweet bulunamadı. Id: " + id, HttpStatus.NOT_FOUND);
    }

    public RetweetNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
