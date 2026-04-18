package com.workintech.twitter_api.dto.response;

import java.time.LocalDateTime;

public record UserResponseDto(
        String        username,
        String        email,
        String        bio,
        LocalDateTime createdAt
) {


}
