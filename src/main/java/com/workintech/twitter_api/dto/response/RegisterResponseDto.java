package com.workintech.twitter_api.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterResponseDto(

        String email,

        String bio

) {
}
