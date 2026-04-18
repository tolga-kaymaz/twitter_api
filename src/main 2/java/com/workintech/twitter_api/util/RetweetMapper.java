package com.workintech.twitter_api.util;

import com.workintech.twitter_api.dto.response.RetweetResponseDto;
import com.workintech.twitter_api.entity.Retweet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetweetMapper {
    private final UserMapper userMapper;

    public RetweetResponseDto toResponseDto(Retweet retweet) {
        if (retweet == null) return null;
        return new RetweetResponseDto(
                retweet.getCreatedAt(),
                userMapper.toResponseDto(retweet.getUser()),
                retweet.getTweet() != null ? retweet.getTweet().getId() : null
        );
    }
}
