package com.workintech.twitter_api.exceptions;

import org.springframework.http.HttpStatus;

public class TweetNotFoundException extends TwitterApiException{

    public TweetNotFoundException(Long id) {

        super("Tweet bulunamadı. Id: " + id, HttpStatus.NOT_FOUND);
    }

    public TweetNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
