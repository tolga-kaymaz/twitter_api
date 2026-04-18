package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.TweetPatchRequestDto;
import com.workintech.twitter_api.dto.request.TweetRequestDto;
import com.workintech.twitter_api.dto.response.TweetResponseDto;
import com.workintech.twitter_api.entity.Tweet;
import com.workintech.twitter_api.entity.User;

import java.util.List;

public interface TweetService {


    TweetResponseDto create(TweetRequestDto dto, User user);

    List<TweetResponseDto> findByUserId(Long userId);

    TweetResponseDto findById(Long id);

    TweetResponseDto update(Long id, TweetPatchRequestDto tweetPatchRequestDto, User user);

    TweetResponseDto replace(Long id, TweetRequestDto dto, User user);

    void delete(Long id, User user);

    Tweet getEntityById(Long id);
}
