package com.workintech.twitter_api.dto.response;

import java.time.LocalDateTime;

public record LikeResponseDto(
        LocalDateTime createdAt,
        UserResponseDto user,
        Long            tweetId
) {
}
