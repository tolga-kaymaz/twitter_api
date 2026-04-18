package com.workintech.twitter_api.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends TwitterApiException{

    public UserNotFoundException(Long id) {
        super("Kullanıcı bulunamadı. Id: " + id, HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
