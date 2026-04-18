package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.dto.request.LikeRequestDto;
import com.workintech.twitter_api.dto.response.LikeResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("like")
public class LikeController {

    @Autowired
    private final LikeService likeService;

    @GetMapping("/findByTweetId")
    public List<LikeResponseDto> findByTweetId(@RequestParam Long tweetId) {
        return likeService.findByTweetId(tweetId);
    }

    @PostMapping("/like")
    public ResponseEntity<LikeResponseDto> like(
            @Valid @RequestBody LikeRequestDto dto,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(likeService.like(dto, currentUser));
    }


    @PostMapping("/dislike")
    public ResponseEntity<LikeResponseDto> dislike(
            @Valid @RequestBody LikeRequestDto dto,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(likeService.dislike(dto, currentUser));
    }

}
