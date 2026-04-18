package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.dto.request.RetweetRequestDto;
import com.workintech.twitter_api.dto.response.RetweetResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.service.RetweetService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/retweet")
public class RetweetController {


    @Autowired
    private final RetweetService retweetService;


    @PostMapping
    public ResponseEntity<RetweetResponseDto> retweet(
            @Validated @RequestBody RetweetRequestDto dto,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(retweetService.retweet(dto, currentUser));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<RetweetResponseDto> delete(
            @Positive @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(retweetService.delete(id, currentUser));
    }

    @GetMapping("/findByUserId")
    public List<RetweetResponseDto> findByUserId(@Positive@RequestParam Long userId) {
        return retweetService.findByUserId(userId);
    }
}

