package com.workintech.twitter_api.dto.response;

import java.time.LocalDateTime;

public record RetweetResponseDto(
        Long id,
        LocalDateTime createdAt,
        UserResponseDto user,
        Long            tweetId,
        TweetResponseDto tweet
) {
}
