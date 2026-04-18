package com.workintech.twitter_api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * TwitterApiException ve ondan türeyen tüm NotFound exception'ları yakalar.
     * (UserNotFoundException, TweetNotFoundException, CommentNotFoundException vb.)
     */
    @ExceptionHandler(TwitterApiException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            TwitterApiException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                ex.getMessage(),
                ex.getHttpStatus().value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    /**
     * Path variable'ın yanlış tipte gönderilmesi durumu.
     * Örn: /tweet/abc gibi Long bekleyen yere String gönderilmesi.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            MethodArgumentTypeMismatchException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * @Valid annotation'ının tetiklediği doğrulama hataları.
     * Örn: boş content ile tweet oluşturmaya çalışmak.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            MethodArgumentNotValidException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Spring Security — yetkisiz erişim (403 Forbidden).
     * Örn: başkasının tweetini silmeye çalışmak.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            AccessDeniedException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                "Bu işlem için yetkiniz yok.",
                HttpStatus.FORBIDDEN.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Spring Security — kimlik doğrulama hatası (401 Unauthorized).
     * Örn: geçersiz kullanıcı adı/şifre ile giriş yapmaya çalışmak.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<TwitterErrorResponse> handleException(
            AuthenticationException ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                "Kimlik doğrulama başarısız.",
                HttpStatus.UNAUTHORIZED.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Beklenmeyen tüm hatalar — 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<TwitterErrorResponse> handle(Exception ex) {

        TwitterErrorResponse errorResponse = new TwitterErrorResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis(),
                LocalDateTime.now()
        );

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
