package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.RetweetRequestDto;
import com.workintech.twitter_api.dto.response.RetweetResponseDto;
import com.workintech.twitter_api.entity.User;

import java.util.List;

public interface RetweetService {

    RetweetResponseDto retweet(RetweetRequestDto dto, User currentUser);

    RetweetResponseDto delete(Long id, User currentUser);

    List<RetweetResponseDto> findByUserId(Long userId);
}
