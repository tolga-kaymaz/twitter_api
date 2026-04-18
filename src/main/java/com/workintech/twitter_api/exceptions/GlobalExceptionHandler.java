package com.workintech.twitter_api.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(TwitterApiException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            TwitterApiException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                ex.getMessage(),
                ex.getHttpStatus().value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );



        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            MethodArgumentTypeMismatchException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );



        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            MethodArgumentNotValidException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );



        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            AccessDeniedException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                "Bu işlem için yetkiniz yok.",
                HttpStatus.FORBIDDEN.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );



        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            AuthenticationException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                "Kimlik doğrulama başarısız.",
                HttpStatus.UNAUTHORIZED.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );



        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<TwitterErrorResponse> handle(Exception ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );



        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
