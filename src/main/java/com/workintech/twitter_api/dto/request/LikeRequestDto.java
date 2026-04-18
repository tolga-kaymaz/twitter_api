package com.workintech.twitter_api.dto.request;

import jakarta.validation.constraints.NotNull;

public record LikeRequestDto(

        @NotNull(message = "Tweet id zorunludur.")
        Long tweetId
) {
}
