package com.workintech.twitter_api.exceptions;

import org.springframework.http.HttpStatus;

public class LikeNotFoundException extends TwitterApiException{

    public LikeNotFoundException(Long id) {
        super("Beğeni bulunamadı. Id: " + id, HttpStatus.NOT_FOUND);
    }

    public LikeNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
