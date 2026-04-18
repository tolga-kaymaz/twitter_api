package com.workintech.twitter_api.dto.response;

import java.time.LocalDateTime;

public record TweetResponseDto(
        String          content,
        LocalDateTime createdAt,
        LocalDateTime   updatedAt,
        UserResponseDto user,
        int             likeCount,
        int             retweetCount,
        int             commentCount
) {
}
