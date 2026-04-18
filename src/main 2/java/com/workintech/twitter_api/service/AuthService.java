package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.LoginRequestDto;
import com.workintech.twitter_api.dto.request.RegisterRequestDto;
import com.workintech.twitter_api.dto.response.AuthResponseDto;

public interface AuthService {

    AuthResponseDto register(RegisterRequestDto registerRequestDto);

    AuthResponseDto login(LoginRequestDto loginRequestDto);
}
