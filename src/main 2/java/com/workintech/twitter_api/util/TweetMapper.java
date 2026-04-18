package com.workintech.twitter_api.util;

import com.workintech.twitter_api.dto.response.TweetResponseDto;
import com.workintech.twitter_api.entity.Tweet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TweetMapper {

    private final UserMapper userMapper;

    public TweetResponseDto toResponseDto(Tweet tweet) {
        if (tweet == null) return null;
        return new TweetResponseDto(

                tweet.getContent(),
                tweet.getCreatedAt(),
                tweet.getUpdatedAt(),
                userMapper.toResponseDto(tweet.getUser()),
                tweet.getLikes()    != null ? tweet.getLikes().size()    : 0,
                tweet.getRetweets() != null ? tweet.getRetweets().size() : 0,
                tweet.getComments() != null ? tweet.getComments().size() : 0
        );
    }
}
