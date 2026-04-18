package com.workintech.twitter_api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TwitterApiException extends RuntimeException {

    private HttpStatus httpStatus;

    public TwitterApiException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

}
