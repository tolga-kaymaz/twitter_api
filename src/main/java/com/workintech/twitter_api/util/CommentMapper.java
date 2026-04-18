package com.workintech.twitter_api.util;

import com.workintech.twitter_api.dto.response.CommentResponseDto;
import com.workintech.twitter_api.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentMapper {

    private final UserMapper userMapper;

    public CommentResponseDto toResponseDto(Comment comment) {
        if (comment == null) return null;
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                userMapper.toResponseDto(comment.getUser()),
                comment.getTweet() != null ? comment.getTweet().getId() : null
        );
    }
}
