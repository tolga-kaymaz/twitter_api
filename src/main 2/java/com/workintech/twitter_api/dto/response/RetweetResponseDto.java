package com.workintech.twitter_api.dto.response;

import java.time.LocalDateTime;

public record RetweetResponseDto(
        LocalDateTime createdAt,
        UserResponseDto user,
        Long            tweetId
) {
}
