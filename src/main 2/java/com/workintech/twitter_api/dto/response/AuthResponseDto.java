package com.workintech.twitter_api.dto.response;

public record AuthResponseDto(
        String          message,
        UserResponseDto user
) {
}
