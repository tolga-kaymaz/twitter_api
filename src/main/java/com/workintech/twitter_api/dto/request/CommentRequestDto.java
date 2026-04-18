package com.workintech.twitter_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequestDto (

        @NotBlank(message = "Yorum içeriği boş olamaz.")
        String content,

        @NotNull(message = "Tweet id zorunludur.")
        Long tweetId
) {
}
