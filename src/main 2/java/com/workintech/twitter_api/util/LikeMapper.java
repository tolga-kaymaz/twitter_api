package com.workintech.twitter_api.util;

import com.workintech.twitter_api.dto.response.LikeResponseDto;
import com.workintech.twitter_api.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeMapper {

    private final UserMapper userMapper;

    public LikeResponseDto toResponseDto(Like like) {
        if (like == null) return null;
        return new LikeResponseDto(
                like.getCreatedAt(),
                userMapper.toResponseDto(like.getUser()),
                like.getTweet() != null ? like.getTweet().getId() : null
        );
    }
}
