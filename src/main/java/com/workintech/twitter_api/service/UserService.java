package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.UserPatchRequestDto;
import com.workintech.twitter_api.dto.request.UserRequestDto;
import com.workintech.twitter_api.dto.response.UserResponseDto;
import com.workintech.twitter_api.entity.User;

import java.util.List;

public interface UserService {


    List<UserResponseDto> getAll();
    List<UserResponseDto> searchByUsername(String username);
    UserResponseDto findById(Long id);
    UserResponseDto replace(Long id, UserRequestDto userRequestDto, User user);
    UserResponseDto update(Long id, UserPatchRequestDto userPatchRequestDto, User user);
    void delete(Long id, User user);



}
