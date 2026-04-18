package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.dto.request.RetweetRequestDto;
import com.workintech.twitter_api.dto.response.RetweetResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.service.RetweetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("/retweet")
public class RetweetController {

    private final RetweetService retweetService;

    /**
     * POST /retweet
     * Tweet'i retweet eder.
     * Body: { "tweetId": 1 }
     */
    @PostMapping
    public ResponseEntity<RetweetResponseDto> retweet(
            @Valid @RequestBody RetweetRequestDto dto,
            @AuthenticationPrincipal User currentUser) {
        log.info("POST /retweet — user: {}, tweetId: {}",
                currentUser.getUsername(), dto.tweetId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(retweetService.retweet(dto, currentUser));
    }

    /**
     * DELETE /retweet/{id}
     * Retweet'i siler. Sadece retweet sahibi yapabilir.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RetweetResponseDto> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        log.info("DELETE /retweet/{} — user: {}", id, currentUser.getUsername());
        return ResponseEntity.ok(retweetService.delete(id, currentUser));
    }
}

