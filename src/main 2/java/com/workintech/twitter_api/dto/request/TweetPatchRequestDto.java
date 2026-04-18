package com.workintech.twitter_api.dto.request;

import jakarta.validation.constraints.Size;

public record TweetPatchRequestDto(
        @Size(max = 280, message = "Tweet en fazla 280 karakter olabilir.")
        String content
) {
}
