package com.workintech.twitter_api.dto.response;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String        username,
        String        email,
        String        bio,
        LocalDateTime createdAt
) {


}
