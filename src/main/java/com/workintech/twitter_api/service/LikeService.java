package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.LikeRequestDto;
import com.workintech.twitter_api.dto.response.LikeResponseDto;
import com.workintech.twitter_api.entity.User;

import java.util.List;

public interface LikeService {

    LikeResponseDto like(LikeRequestDto dto, User currentUser);

    LikeResponseDto dislike(LikeRequestDto dto, User currentUser);

    List<LikeResponseDto> findByTweetId(Long tweetId);
}
