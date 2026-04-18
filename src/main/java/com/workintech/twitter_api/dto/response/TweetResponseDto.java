package com.workintech.twitter_api.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record TweetResponseDto(
        Long id,
        String          content,
        LocalDateTime createdAt,
        LocalDateTime   updatedAt,
        UserResponseDto user,
        int             likeCount,
        int             retweetCount,
        int             commentCount,
        List<CommentResponseDto> comments,
        List<LikeResponseDto> likes
) {
}
