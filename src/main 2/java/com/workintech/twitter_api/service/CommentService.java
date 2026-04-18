package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.CommentPatchRequestDto;
import com.workintech.twitter_api.dto.request.CommentRequestDto;
import com.workintech.twitter_api.dto.response.CommentResponseDto;
import com.workintech.twitter_api.entity.User;

import java.util.List;

public interface CommentService {

    CommentResponseDto create(CommentRequestDto commentRequestDto, User user);

    List<CommentResponseDto> findByTweetId(Long tweetId);

    CommentResponseDto findById(Long id);

    CommentResponseDto update(Long id, CommentPatchRequestDto commentPatchRequestDto, User user);

    CommentResponseDto replace(Long id, CommentRequestDto commentRequestDto, User user);

    void delete(Long id, User user);
}
