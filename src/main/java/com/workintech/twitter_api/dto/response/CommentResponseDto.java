package com.workintech.twitter_api.dto.response;

import java.time.LocalDateTime;

public record CommentResponseDto(
        Long id,
        String          content,
        LocalDateTime   createdAt,
        LocalDateTime updatedAt,
        UserResponseDto user,
        Long            tweetId
) {
}
