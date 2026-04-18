package com.workintech.twitter_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDto(

        @NotNull
        @NotBlank
        @NotEmpty
        @Size(max = 150)
        String   username,

        @NotNull
        @NotBlank
        @NotEmpty
        @Size(max = 250)
        String   email,

        String    bio
) {
}
