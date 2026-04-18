package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.dto.request.LikeRequestDto;
import com.workintech.twitter_api.dto.response.LikeResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class LikeController {
    private LikeService likeService;

    @GetMapping("/findByTweetId")
    public List<LikeResponseDto> findByTweetId(@RequestParam Long tweetId) {
        return likeService.findByTweetId(tweetId);
    }

    @PostMapping("/like")
    public ResponseEntity<LikeResponseDto> like(
            @Valid @RequestBody LikeRequestDto dto,
            @AuthenticationPrincipal User currentUser) {
        log.info("POST /like — user: {}, tweetId: {}",
                currentUser.getUsername(), dto.tweetId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(likeService.like(dto, currentUser));
    }


    @PostMapping("/dislike")
    public ResponseEntity<LikeResponseDto> dislike(
            @Valid @RequestBody LikeRequestDto dto,
            @AuthenticationPrincipal User currentUser) {
        log.info("POST /dislike — user: {}, tweetId: {}",
                currentUser.getUsername(), dto.tweetId());
        return ResponseEntity.ok(likeService.dislike(dto, currentUser));
    }

}
