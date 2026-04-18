package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.dto.request.LoginRequestDto;
import com.workintech.twitter_api.dto.request.RegisterRequestDto;
import com.workintech.twitter_api.dto.response.AuthResponseDto;
import com.workintech.twitter_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {

    private  AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @Valid @RequestBody RegisterRequestDto registerRequestDto) {
        log.info("POST /register — {}", registerRequestDto.username());
        AuthResponseDto response = authService.register(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto) {
        log.info("POST /login — {}", loginRequestDto.email());
        AuthResponseDto response = authService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }
}
