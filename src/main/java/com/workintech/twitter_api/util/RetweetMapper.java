package com.workintech.twitter_api.util;

import com.workintech.twitter_api.dto.response.RetweetResponseDto;
import com.workintech.twitter_api.entity.Retweet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RetweetMapper {
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;


    public RetweetResponseDto toResponseDto(Retweet retweet) {
        if (retweet == null) return null;
        return new RetweetResponseDto(
                retweet.getId(),
                retweet.getCreatedAt(),
                userMapper.toResponseDto(retweet.getUser()),
                retweet.getTweet() != null ? retweet.getTweet().getId() : null,
                retweet.getTweet() != null ? tweetMapper.toResponseDto(retweet.getTweet()) : null
        );

    }
}
